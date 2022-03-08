package com.michaelmccormick.usermanager.core.models.errors

sealed class CreateUserError : Exception() {
    object EmailInUse : CreateUserError()
    object InvalidEmail : CreateUserError()
    object Unknown : CreateUserError()
}
