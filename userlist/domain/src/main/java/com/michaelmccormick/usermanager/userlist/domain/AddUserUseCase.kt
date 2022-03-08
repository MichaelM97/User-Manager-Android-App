package com.michaelmccormick.usermanager.userlist.domain

import com.michaelmccormick.usermanager.core.models.Gender
import com.michaelmccormick.usermanager.core.models.User
import com.michaelmccormick.usermanager.repositories.interfaces.UserRepository
import javax.inject.Inject

class AddUserUseCase @Inject internal constructor(
    private val repository: UserRepository,
) {
    suspend fun execute(name: String, email: String, gender: Gender): Result<Unit> =
        repository.createNewUser(name, email, gender)
}
