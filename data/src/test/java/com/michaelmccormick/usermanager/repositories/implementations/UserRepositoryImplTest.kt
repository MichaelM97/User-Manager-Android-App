package com.michaelmccormick.usermanager.repositories.implementations

import com.google.gson.Gson
import com.michaelmccormick.usermanager.core.models.Gender
import com.michaelmccormick.usermanager.core.models.User
import com.michaelmccormick.usermanager.core.models.errors.CreateUserError
import com.michaelmccormick.usermanager.core.test.MockExtension
import com.michaelmccormick.usermanager.network.interfaces.UserService
import com.michaelmccormick.usermanager.network.models.UserNetworkEntity
import com.michaelmccormick.usermanager.network.models.createUserEntity
import com.michaelmccormick.usermanager.repositories.mappers.toUser
import com.michaelmccormick.usermanager.repositories.models.GoRestError
import com.michaelmccormick.usermanager.repositories.models.parseGoRestErrorBody
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
@ExtendWith(MockExtension::class)
internal class UserRepositoryImplTest {
    private val mockUserService: UserService = mockk()
    private val mockGson: Gson = mockk()
    private lateinit var userRepositoryImpl: UserRepositoryImpl

    @BeforeEach
    fun before() {
        userRepositoryImpl = UserRepositoryImpl(mockUserService, mockGson)
    }

    @Test
    fun shouldInitUserListAsEmpty() {
        assertEquals(emptyList(), userRepositoryImpl.userList.value)
    }

    @Nested
    inner class RefreshUserList {
        @Test
        fun shouldEmitUserListAndReturnSuccess() = runTest {
            // Given
            coEvery { mockUserService.getUsers() } returns userEntityList

            // When
            val returnedValue = userRepositoryImpl.refreshUserList()

            // Then
            coVerify(exactly = 1) { mockUserService.getUsers() }
            assertEquals(userList, userRepositoryImpl.userList.first())
            assertEquals(Result.success(Unit), returnedValue)
        }

        @Test
        fun shouldReturnFailureWhenServiceThrows() = runTest {
            // Given
            val exception: HttpException = mockk()
            coEvery { mockUserService.getUsers() } throws exception

            // When
            val returnedValue = userRepositoryImpl.refreshUserList()

            // Then
            coVerify(exactly = 1) { mockUserService.getUsers() }
            assertEquals(emptyList(), userRepositoryImpl.userList.first())
            assertEquals(Result.failure(exception), returnedValue)
        }
    }

    @Nested
    inner class CreateNewUser {
        @Test
        fun shouldEmitUpdatedListAndReturnSuccess() = runTest {
            // Given
            coEvery { mockUserService.getUsers() } returns userEntityList
            userRepositoryImpl.refreshUserList()
            val name = "Chris"
            val email = "chris@email.com"
            val gender = Gender.MALE
            val userNetworkEntity = createUserEntity(name, email, gender.value)
            coEvery { mockUserService.createUser(any()) } returns userNetworkEntity

            // When
            val returnedValue = userRepositoryImpl.createNewUser(name, email, gender)

            // Then
            val updatedList = userList.toMutableList().apply { add(userNetworkEntity.toUser()) }
            coVerify(exactly = 1) { mockUserService.createUser(userNetworkEntity) }
            assertEquals(updatedList, userRepositoryImpl.userList.first())
            assertEquals(Result.success(Unit), returnedValue)
        }

        @ParameterizedTest
        @ArgumentsSource(CreateNewUserArgumentsProvider::class)
        fun shouldReturnFailureWhenServiceThrows(
            errorMessage: String,
            expectedError: CreateUserError,
        ) = runTest {
            // Given
            val exception = buildMockHttpException(errorMessage = errorMessage)
            coEvery { mockUserService.createUser(any()) } throws exception

            // When
            val returnedValue = userRepositoryImpl.createNewUser("", "", Gender.MALE)

            // Then
            coVerify(exactly = 1) { mockUserService.createUser(createUserEntity(name = "", email = "", gender = "male")) }
            assertEquals(emptyList(), userRepositoryImpl.userList.first())
            assertEquals(Result.failure(expectedError), returnedValue)
        }

        private fun buildMockHttpException(errorMessage: String): HttpException {
            val mockHttpException: HttpException = mockk()
            every { mockHttpException.parseGoRestErrorBody(mockGson) } returns arrayOf(
                GoRestError(message = errorMessage)
            )
            return mockHttpException
        }
    }

    @Nested
    inner class RemoveUser {
        @Test
        fun shouldEmitUpdatedListAndReturnSuccess() = runTest {
            // Given
            coEvery { mockUserService.getUsers() } returns userEntityList
            userRepositoryImpl.refreshUserList()
            coEvery { mockUserService.deleteUser(any()) } returns Response.success(Unit)

            // When
            val returnedValue = userRepositoryImpl.removeUser(2)

            // Then
            val updatedList = userList.toMutableList().apply { removeAt(1) }
            coVerify(exactly = 1) { mockUserService.deleteUser(2) }
            assertEquals(updatedList, userRepositoryImpl.userList.first())
            assertEquals(Result.success(Unit), returnedValue)
        }

        @Test
        fun shouldReturnFailureWhenServiceThrows() = runTest {
            // Given
            val exception: HttpException = mockk()
            coEvery { mockUserService.deleteUser(any()) } throws exception

            // When
            val returnedValue = userRepositoryImpl.removeUser(1)

            // Then
            coVerify(exactly = 1) { mockUserService.deleteUser(1) }
            assertEquals(emptyList(), userRepositoryImpl.userList.first())
            assertEquals(Result.failure(exception), returnedValue)
        }
    }

    private companion object {
        val userEntityList = listOf(
            UserNetworkEntity(id = 1, name = "Mike", email = "mike@email.com"),
            UserNetworkEntity(id = 2, name = "Gemma", email = "gemma@email.com"),
            UserNetworkEntity(id = 5, name = "John", email = "john@mail.com"),
        )

        val userList = listOf(
            User(id = 1, name = "Mike", email = "mike@email.com"),
            User(id = 2, name = "Gemma", email = "gemma@email.com"),
            User(id = 5, name = "John", email = "john@mail.com"),
        )
    }

    private class CreateNewUserArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<Arguments> = Stream.of(
            Arguments.of("has already been taken", CreateUserError.EmailInUse),
            Arguments.of("is invalid", CreateUserError.InvalidEmail),
            Arguments.of("unknown", CreateUserError.Unknown),
            Arguments.of("", CreateUserError.Unknown),
        )
    }
}
