package com.michaelmccormick.usermanager.userlist.domain

import com.michaelmccormick.usermanager.repositories.interfaces.UserRepository
import javax.inject.Inject

class RemoveUserUseCase @Inject internal constructor(
    private val repository: UserRepository,
) {
    suspend fun execute(userId: Int): Result<Unit> = repository.removeUser(userId)
}
