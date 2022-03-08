package com.michaelmccormick.usermanager.userlist.domain

import com.michaelmccormick.usermanager.core.models.User
import com.michaelmccormick.usermanager.core.test.MockExtension
import com.michaelmccormick.usermanager.repositories.interfaces.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockExtension::class)
internal class GetUserListUseCaseTest {
    private val mockUserRepository: UserRepository = mockk()
    private lateinit var useCase: GetUserListUseCase

    @BeforeEach
    fun before() {
        useCase = GetUserListUseCase(mockUserRepository)
    }

    @Test
    fun shouldReturnUserListFlowFromRepository() {
        // Given
        val flow: StateFlow<List<User>> = MutableStateFlow(emptyList())
        every { mockUserRepository.userList } returns flow

        // When
        val returnedValue = useCase.execute()

        // Then
        verify(exactly = 1) { mockUserRepository.userList }
        assertEquals(flow, returnedValue)
    }
}
