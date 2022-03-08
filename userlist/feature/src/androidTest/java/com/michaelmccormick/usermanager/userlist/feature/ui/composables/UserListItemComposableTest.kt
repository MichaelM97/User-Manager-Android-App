package com.michaelmccormick.usermanager.userlist.feature.ui.composables

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import com.michaelmccormick.usermanager.core.models.User
import com.michaelmccormick.usermanager.core.uitest.BaseUiTest
import org.junit.Test

internal class UserListItemComposableTest : BaseUiTest() {
    @Test
    fun shouldDisplayNameAndEmail() {
        // Given
        val user = User(1, "Mike", "mike@email.com")

        // When
        composeTestRule.setContent { UserListItemComposable(user, Modifier) }

        // Then
        composeTestRule.onNodeWithText("Mike").assertIsDisplayed()
        composeTestRule.onNodeWithText("mike@email.com").assertIsDisplayed()
    }
}
