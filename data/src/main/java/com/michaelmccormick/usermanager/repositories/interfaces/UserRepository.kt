package com.michaelmccormick.usermanager.repositories.interfaces

import com.michaelmccormick.usermanager.core.models.Gender
import com.michaelmccormick.usermanager.core.models.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val userList: StateFlow<List<User>>
    suspend fun refreshUserList(): Result<Unit>
    suspend fun createNewUser(name: String, email: String, gender: Gender): Result<Unit>
    suspend fun removeUser(userId: Int): Result<Unit>
}
