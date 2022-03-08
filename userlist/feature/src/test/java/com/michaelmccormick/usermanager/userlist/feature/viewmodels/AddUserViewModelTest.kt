package com.michaelmccormick.usermanager.userlist.feature.viewmodels

import com.michaelmccormick.usermanager.core.models.Gender
import com.michaelmccormick.usermanager.core.models.errors.CreateUserError
import com.michaelmccormick.usermanager.core.test.DispatchersExtension
import com.michaelmccormick.usermanager.core.test.MockExtension
import com.michaelmccormick.usermanager.userlist.domain.AddUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource

@ExperimentalCoroutinesApi
@ExtendWith(MockExtension::class, DispatchersExtension::class)
internal class AddUserViewModelTest {
    private val mockAddUserUseCase: AddUserUseCase = mockk()
    private lateinit var viewModel: AddUserViewModel

    @BeforeEach
    fun before() {
        viewModel = AddUserViewModel(mockAddUserUseCase)
    }

    @Nested
    inner class Init {
        @Test
        fun shouldHaveCorrectInitialState() {
            assertEquals("", viewModel.name)
            assertEquals("", viewModel.email)
            assertEquals(Gender.MALE, viewModel.gender)
            assertEquals(false, viewModel.loading)
            assertEquals(null, viewModel.navigationState)
            assertEquals(null, viewModel.errorState)
        }
    }

    @Nested
    inner class OnNameChanged {
        @Test
        fun shouldSetName() {
            // When
            viewModel.onNameChanged("Mike")

            // Then
            assertEquals("Mike", viewModel.name)
        }

        @Test
        fun shouldClearErrorState() {
            // Given
            viewModel.onAddUser()
            assertEquals(AddUserViewModel.ErrorState.EMPTY_NAME, viewModel.errorState)

            // When
            viewModel.onNameChanged("Test")

            // Then
            assertEquals(null, viewModel.errorState)
        }
    }

    @Nested
    inner class OnEmailChanged {
        @Test
        fun shouldSetEmail() {
            // When
            viewModel.onEmailChanged("mike@email.com")

            // Then
            assertEquals("mike@email.com", viewModel.email)
        }

        @Test
        fun shouldClearErrorState() {
            // Given
            viewModel.onAddUser()
            assertEquals(AddUserViewModel.ErrorState.EMPTY_NAME, viewModel.errorState)

            // When
            viewModel.onEmailChanged("Test")

            // Then
            assertEquals(null, viewModel.errorState)
        }
    }

    @Nested
    inner class OnGenderSelected {
        @Test
        fun shouldSetGender() {
            // When
            viewModel.onGenderSelected(Gender.FEMALE)

            // Then
            assertEquals(Gender.FEMALE, viewModel.gender)
        }
    }

    @Nested
    inner class OnAddUser {
        @Test
        fun shouldSetErrorIfNameBlank() {
            // Given
            viewModel.onNameChanged("    ")

            // When
            viewModel.onAddUser()

            // Then
            assertEquals(AddUserViewModel.ErrorState.EMPTY_NAME, viewModel.errorState)
        }

        @Test
        fun shouldSetErrorIfEmailBlank() {
            // Given
            viewModel.onNameChanged("Mike")
            viewModel.onEmailChanged("    ")

            // When
            viewModel.onAddUser()

            // Then
            assertEquals(AddUserViewModel.ErrorState.EMPTY_EMAIL, viewModel.errorState)
        }

        @Test
        fun shouldSetNavigationStateWhenSuccess() {
            // Given
            val name = "Gemma"
            viewModel.onNameChanged(name)
            val email = "gemma@email.com"
            viewModel.onEmailChanged(email)
            val gender = Gender.FEMALE
            viewModel.onGenderSelected(gender)
            coEvery { mockAddUserUseCase.execute(name, email, gender) } returns Result.success(Unit)

            // When
            viewModel.onAddUser()

            // Then
            coVerify(exactly = 1) { mockAddUserUseCase.execute(name, email, gender) }
            assertEquals(true, viewModel.loading)
            assertEquals(AddUserViewModel.NavigationState.CLOSE_AFTER_SUCCESS, viewModel.navigationState)
        }

        @ParameterizedTest
        @ArgumentsSource(OnAddUserArgumentsProvider::class)
        fun shouldSetErrorWhenFailure(
            failureException: Exception,
            expectedErrorState: AddUserViewModel.ErrorState,
        ) {
            // Given
            val name = "Mike"
            viewModel.onNameChanged(name)
            val email = "mike@email.com"
            viewModel.onEmailChanged(email)
            val gender = Gender.MALE
            viewModel.onGenderSelected(gender)
            coEvery { mockAddUserUseCase.execute(name, email, gender) } returns Result.failure(failureException)

            // When
            viewModel.onAddUser()

            // Then
            coVerify(exactly = 1) { mockAddUserUseCase.execute(name, email, gender) }
            assertEquals(expectedErrorState, viewModel.errorState)
            assertEquals(false, viewModel.loading)
        }
    }

    @Nested
    inner class OnCancel {
        @Test
        fun shouldSetNavigationState() {
            // When
            viewModel.onCancel()

            // Then
            assertEquals(AddUserViewModel.NavigationState.CLOSE, viewModel.navigationState)
        }

        @Test
        fun shouldResetErrorState() {
            // Given
            viewModel.onAddUser()
            assertEquals(AddUserViewModel.ErrorState.EMPTY_NAME, viewModel.errorState)

            // When
            viewModel.onCancel()

            // Then
            assertEquals(null, viewModel.errorState)
        }
    }

    private class OnAddUserArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<Arguments> = Stream.of(
            Arguments.of(CreateUserError.EmailInUse, AddUserViewModel.ErrorState.EMAIL_IN_USE),
            Arguments.of(CreateUserError.InvalidEmail, AddUserViewModel.ErrorState.INVALID_EMAIL),
            Arguments.of(CreateUserError.Unknown, AddUserViewModel.ErrorState.ADD_USER_FAILURE),
            Arguments.of(Exception(), AddUserViewModel.ErrorState.ADD_USER_FAILURE),
        )
    }
}
