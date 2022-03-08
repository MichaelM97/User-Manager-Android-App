package com.michaelmccormick.usermanager.network.models

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

internal class UserNetworkEntityTest {
    @Test
    fun shouldCreateUserEntity() {
        // Given
        val name = "John"
        val email = "john@email.com"
        val gender = "male"

        // When
        val entity = createUserEntity(name, email, gender)

        // Then
        assertEquals(
            UserNetworkEntity(
                id = null,
                name = name,
                email = email,
                gender = gender,
                status = "active",
            ),
            entity,
        )
    }
}
