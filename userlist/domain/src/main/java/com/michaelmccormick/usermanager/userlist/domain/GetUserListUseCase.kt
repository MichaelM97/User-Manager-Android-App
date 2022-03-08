package com.michaelmccormick.usermanager.userlist.domain

import com.michaelmccormick.usermanager.core.models.User
import com.michaelmccormick.usermanager.repositories.interfaces.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

class GetUserListUseCase @Inject internal constructor(
    private val repository: UserRepository,
) {
    fun execute(): StateFlow<List<User>> = repository.userList
}
