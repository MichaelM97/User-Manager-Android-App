package com.michaelmccormick.usermanager.repositories.mappers

import com.michaelmccormick.usermanager.core.models.User
import com.michaelmccormick.usermanager.network.models.UserNetworkEntity

internal fun UserNetworkEntity.toUser() = User(
    id = id ?: 0,
    name = name.orEmpty(),
    email = email.orEmpty(),
)
