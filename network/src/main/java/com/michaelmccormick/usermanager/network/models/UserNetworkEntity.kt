package com.michaelmccormick.usermanager.network.models

data class UserNetworkEntity(
    val id: Int? = null,
    val name: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val status: String? = null,
)

fun createUserEntity(
    name: String,
    email: String,
    gender: String,
) = UserNetworkEntity(
    id = null,
    name = name,
    email = email,
    gender = gender,
    status = "active",
)
