package com.michaelmccormick.usermanager.userlist.feature.viewmodels

import com.michaelmccormick.usermanager.core.models.User
import com.michaelmccormick.usermanager.core.test.DispatchersExtension
import com.michaelmccormick.usermanager.core.test.MockExtension
import com.michaelmccormick.usermanager.userlist.domain.GetUserListUseCase
import com.michaelmccormick.usermanager.userlist.domain.RefreshUserListUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockExtension::class, DispatchersExtension::class)
internal class UserListViewModelTest {
    private val mockGetUserListUseCase: GetUserListUseCase = mockk()
    private val mockRefreshUserListUseCase: RefreshUserListUseCase = mockk()
    private lateinit var viewModel: UserListViewModel

    @Nested
    inner class Init {
        @Test
        fun shouldHaveCorrectInitialState() {
            // Given
            every { mockGetUserListUseCase.execute() } returns MutableStateFlow(emptyList())
            coEvery { mockRefreshUserListUseCase.execute() } returns Result.success(Unit)

            // When
            buildViewModel()

            // Then
            assertEquals(true, viewModel.loading)
            assertEquals(emptyList(), viewModel.userList)
            assertEquals(null, viewModel.navigationState)
            assertEquals(null, viewModel.errorState)
        }

        @Test
        fun shouldGetUserList() {
            // Given
            every { mockGetUserListUseCase.execute() } returns MutableStateFlow(userList)
            coEvery { mockRefreshUserListUseCase.execute() } returns Result.success(Unit)

            // When
            buildViewModel()

            // Then
            assertEquals(userList, viewModel.userList)
        }

        @Test
        fun shouldRefreshUserList() {
            // Given
            every { mockGetUserListUseCase.execute() } returns MutableStateFlow(emptyList())
            coEvery { mockRefreshUserListUseCase.execute() } returns Result.success(Unit)

            // When
            buildViewModel()

            // Then
            coVerify(exactly = 1) { mockRefreshUserListUseCase.execute() }
        }

        @Test
        fun shouldSetErrorWhenRefreshUserListFails() {
            // Given
            every { mockGetUserListUseCase.execute() } returns MutableStateFlow(emptyList())
            coEvery { mockRefreshUserListUseCase.execute() } returns Result.failure(Exception())

            // When
            buildViewModel()

            // Then
            coVerify(exactly = 1) { mockRefreshUserListUseCase.execute() }
            assertEquals(UserListViewModel.ErrorState.GET_USER_LIST_FAILURE, viewModel.errorState)
            assertEquals(false, viewModel.loading)
        }
    }

    @Nested
    inner class OnRetryUserListFetch {
        @Test
        fun shouldRefreshUserList() {
            // Given
            every { mockGetUserListUseCase.execute() } returns MutableStateFlow(emptyList())
            coEvery { mockRefreshUserListUseCase.execute() } returns Result.success(Unit)
            buildViewModel()

            // When
            viewModel.onRetryUserListFetch()

            // Then
            coVerify(exactly = 2) { mockRefreshUserListUseCase.execute() }
        }

        @Test
        fun shouldSetErrorWhenRefreshUserListFails() {
            // Given
            every { mockGetUserListUseCase.execute() } returns MutableStateFlow(emptyList())
            coEvery { mockRefreshUserListUseCase.execute() } returns Result.failure(Exception())
            buildViewModel()

            // When
            viewModel.onRetryUserListFetch()

            // Then
            coVerify(exactly = 2) { mockRefreshUserListUseCase.execute() }
            assertEquals(UserListViewModel.ErrorState.GET_USER_LIST_FAILURE, viewModel.errorState)
            assertEquals(false, viewModel.loading)
        }

        @Test
        fun shouldClearError() {
            // Given
            every { mockGetUserListUseCase.execute() } returns MutableStateFlow(emptyList())
            coEvery { mockRefreshUserListUseCase.execute() } returns Result.failure(Exception())
            buildViewModel()
            assertEquals(UserListViewModel.ErrorState.GET_USER_LIST_FAILURE, viewModel.errorState)

            // When
            coEvery { mockRefreshUserListUseCase.execute() } returns Result.success(Unit)
            viewModel.onRetryUserListFetch()

            // Then
            assertEquals(null, viewModel.errorState)
        }
    }

    @Nested
    inner class OnAddUserSelected {
        @Test
        fun shouldSetNavigationState() {
            // Given
            buildViewModel()

            // When
            viewModel.onAddUserSelected()

            // Then
            assertEquals(UserListViewModel.NavigationState.AddUser, viewModel.navigationState)
        }
    }

    @Nested
    inner class OnUserLongPressed {
        @Test
        fun shouldSetNavigationState() {
            // Given
            val user = User(1, "Mike", "mike@email.com")
            buildViewModel()

            // When
            viewModel.onUserLongPressed(user)

            // Then
            assertEquals(UserListViewModel.NavigationState.RemoveUser(user), viewModel.navigationState)
        }
    }

    @Nested
    inner class OnNavigationHandled {
        @Test
        fun shouldClearNavigationState() {
            // Given
            buildViewModel()
            viewModel.onAddUserSelected()
            assertEquals(UserListViewModel.NavigationState.AddUser, viewModel.navigationState)

            // When
            viewModel.onNavigationHandled()

            // Then
            assertEquals(null, viewModel.navigationState)
        }
    }

    private fun buildViewModel() {
        viewModel = UserListViewModel(mockGetUserListUseCase, mockRefreshUserListUseCase)
    }

    private companion object {
        val userList = listOf(
            User(id = 1, name = "Mike", email = "mike@email.com"),
            User(id = 2, name = "Gemma", email = "gemma@email.com"),
            User(id = 5, name = "John", email = "john@mail.com"),
        )
    }
}
