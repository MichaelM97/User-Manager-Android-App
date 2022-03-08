package com.michaelmccormick.usermanager

import com.michaelmccormick.usermanager.core.models.Gender
import com.michaelmccormick.usermanager.core.models.User
import com.michaelmccormick.usermanager.repositories.interfaces.UserRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Singleton
class FakeUserRepositoryImpl @Inject constructor() : UserRepository {
    private val _userList = MutableStateFlow(initialUserList)
    override val userList: StateFlow<List<User>> = _userList

    override suspend fun refreshUserList() = Result.success(Unit)

    override suspend fun createNewUser(name: String, email: String, gender: Gender) = Result.success(Unit)

    override suspend fun removeUser(userId: Int) = Result.success(Unit)

    suspend fun addUser(user: User) {
        _userList.emit(userList.value.toMutableList().apply { add(user) })
    }

    private companion object {
        val initialUserList = listOf(
            User(1, "Mike", "mike@email.com"),
            User(2, "Lauren", "lauren@email.com"),
            User(3, "John", "john@email.com"),
        )
    }
}
