package com.michaelmccormick.usermanager.repositories.mappers

import com.michaelmccormick.usermanager.core.models.User
import com.michaelmccormick.usermanager.network.models.UserNetworkEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserNetworkEntityMapperTest {
    @Test
    fun shouldMapEntityToModel() {
        // Given
        val entity = UserNetworkEntity(
            id = 12,
            name = "Test",
            email = "test@test.com",
            gender = "female",
            status = "active",
        )

        // When
        val returnedValue = entity.toUser()

        // Then
        assertEquals(
            User(id = 12, name = "Test", email = "test@test.com"),
            returnedValue,
        )
    }
}
