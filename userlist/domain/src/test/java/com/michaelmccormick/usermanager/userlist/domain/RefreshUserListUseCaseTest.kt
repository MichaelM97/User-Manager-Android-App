package com.michaelmccormick.usermanager.userlist.domain

import com.michaelmccormick.usermanager.core.test.MockExtension
import com.michaelmccormick.usermanager.repositories.interfaces.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockExtension::class)
internal class RefreshUserListUseCaseTest {
    private val mockUserRepository: UserRepository = mockk()
    private lateinit var useCase: RefreshUserListUseCase

    @BeforeEach
    fun before() {
        useCase = RefreshUserListUseCase(mockUserRepository)
    }

    @Test
    fun shouldReturnResultFromRepository() = runTest {
        // Given
        val result = Result.success(Unit)
        coEvery { mockUserRepository.refreshUserList() } returns result

        // When
        val returnedValue = useCase.execute()

        // Then
        coVerify(exactly = 1) { mockUserRepository.refreshUserList() }
        assertEquals(result, returnedValue)
    }
}
