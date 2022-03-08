package com.michaelmccormick.usermanager.userlist.domain

import com.michaelmccormick.usermanager.repositories.interfaces.UserRepository
import javax.inject.Inject

class RefreshUserListUseCase @Inject internal constructor(
    private val repository: UserRepository,
) {
    suspend fun execute(): Result<Unit> = repository.refreshUserList()
}
