package com.michaelmccormick.usermanager.userlist.domain

import com.michaelmccormick.usermanager.core.test.MockExtension
import com.michaelmccormick.usermanager.repositories.interfaces.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockExtension::class)
internal class RemoveUserUseCaseTest {
    private val mockUserRepository: UserRepository = mockk()
    private lateinit var useCase: RemoveUserUseCase

    @BeforeEach
    fun before() {
        useCase = RemoveUserUseCase(mockUserRepository)
    }

    @Test
    fun shouldReturnResultFromRepository() = runTest {
        // Given
        val result = Result.success(Unit)
        coEvery { mockUserRepository.removeUser(1) } returns result

        // When
        val returnedValue = useCase.execute(1)

        // Then
        coVerify(exactly = 1) { mockUserRepository.removeUser(1) }
        assertEquals(result, returnedValue)
    }
}
