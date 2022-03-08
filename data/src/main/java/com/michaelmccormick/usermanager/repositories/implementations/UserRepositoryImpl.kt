package com.michaelmccormick.usermanager.repositories.implementations

import com.google.gson.Gson
import com.michaelmccormick.usermanager.core.models.Gender
import com.michaelmccormick.usermanager.core.models.User
import com.michaelmccormick.usermanager.core.models.errors.CreateUserError
import com.michaelmccormick.usermanager.network.interfaces.UserService
import com.michaelmccormick.usermanager.network.models.createUserEntity
import com.michaelmccormick.usermanager.repositories.interfaces.UserRepository
import com.michaelmccormick.usermanager.repositories.mappers.toUser
import com.michaelmccormick.usermanager.repositories.models.parseGoRestErrorBody
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.HttpException

@Singleton
internal class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val gson: Gson,
) : UserRepository {
    private val _userList = MutableStateFlow<List<User>>(emptyList())
    override val userList: StateFlow<List<User>> = _userList

    override suspend fun refreshUserList(): Result<Unit> {
        return try {
            val list = userService.getUsers()
                .filter { it.id != null && it.name != null && it.email != null }
                .map { it.toUser() }
            _userList.emit(list)
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun createNewUser(name: String, email: String, gender: Gender): Result<Unit> {
        return try {
            val user = userService.createUser(
                createUserEntity(
                    name = name,
                    email = email,
                    gender = gender.value,
                ),
            ).toUser()
            _userList.emit(userList.value.toMutableList().apply { add(user) })
            Result.success(Unit)
        } catch (e: HttpException) {
            val error: CreateUserError = e.parseGoRestErrorBody(gson)?.let {
                when (it.first().message) {
                    EMAIL_IN_USE_ERROR -> CreateUserError.EmailInUse
                    INVALID_EMAIL_ERROR -> CreateUserError.InvalidEmail
                    else -> CreateUserError.Unknown
                }
            } ?: CreateUserError.Unknown
            Result.failure(error)
        }
    }

    override suspend fun removeUser(userId: Int): Result<Unit> {
        return try {
            userService.deleteUser(userId)
            _userList.emit(userList.value.toMutableList().apply { removeAt(indexOfFirst { it.id == userId }) })
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    private companion object {
        const val EMAIL_IN_USE_ERROR = "has already been taken"
        const val INVALID_EMAIL_ERROR = "is invalid"
    }
}
