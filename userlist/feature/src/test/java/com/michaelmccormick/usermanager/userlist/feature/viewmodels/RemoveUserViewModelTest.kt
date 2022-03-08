package com.michaelmccormick.usermanager.userlist.feature.viewmodels

import com.michaelmccormick.usermanager.core.test.DispatchersExtension
import com.michaelmccormick.usermanager.core.test.MockExtension
import com.michaelmccormick.usermanager.userlist.domain.RemoveUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockExtension::class, DispatchersExtension::class)
internal class RemoveUserViewModelTest {
    private val mockRemoveUserUseCase: RemoveUserUseCase = mockk()
    private lateinit var viewModel: RemoveUserViewModel

    @BeforeEach
    fun before() {
        viewModel = RemoveUserViewModel(mockRemoveUserUseCase)
    }

    @Nested
    inner class Init {
        @Test
        fun shouldHaveCorrectInitialState() {
            assertEquals(false, viewModel.loading)
            assertEquals(null, viewModel.navigationState)
            assertEquals(null, viewModel.errorState)
        }
    }

    @Nested
    inner class OnRemoveUser {
        @Test
        fun shouldSetNavigationStateWhenSuccess() {
            // Given
            val userId = 123
            coEvery { mockRemoveUserUseCase.execute(userId) } returns Result.success(Unit)

            // When
            viewModel.onRemoveUser(userId)

            // Then
            coVerify(exactly = 1) { mockRemoveUserUseCase.execute(userId) }
            assertEquals(true, viewModel.loading)
            assertEquals(RemoveUserViewModel.NavigationState.CLOSE_AFTER_SUCCESS, viewModel.navigationState)
        }

        @Test
        fun shouldSetErrorStateWhenFailure() {
            // Given
            val userId = 123
            coEvery { mockRemoveUserUseCase.execute(userId) } returns Result.failure(Exception())

            // When
            viewModel.onRemoveUser(userId)

            // Then
            coVerify(exactly = 1) { mockRemoveUserUseCase.execute(userId) }
            assertEquals(false, viewModel.loading)
            assertEquals(RemoveUserViewModel.ErrorState.REMOVE_USER_FAILURE, viewModel.errorState)
        }

        @Test
        fun shouldResetErrorStateWhenCalled() {
            // Given
            val userId = 123
            coEvery { mockRemoveUserUseCase.execute(userId) } returns Result.failure(Exception())
            viewModel.onRemoveUser(userId)

            // When
            coEvery { mockRemoveUserUseCase.execute(userId) } returns Result.success(Unit)
            viewModel.onRemoveUser(userId)

            // Then
            coVerify(exactly = 2) { mockRemoveUserUseCase.execute(userId) }
            assertEquals(true, viewModel.loading)
            assertEquals(null, viewModel.errorState)
        }
    }

    @Nested
    inner class OnCancel {
        @Test
        fun shouldSetNavigationState() {
            // When
            viewModel.onCancel()

            // Then
            assertEquals(RemoveUserViewModel.NavigationState.CLOSE, viewModel.navigationState)
        }
    }
}
