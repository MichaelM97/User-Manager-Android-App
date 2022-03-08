package com.michaelmccormick.usermanager

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import com.michaelmccormick.usermanager.core.models.User
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@HiltAndroidTest
class MainActivityTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var fakeUserRepositoryImpl: FakeUserRepositoryImpl

    @Before
    fun before() {
        hiltRule.inject()
    }

    @Test
    fun shouldShowUserList() {
        fakeUserRepositoryImpl.userList.value.forEach {
            composeTestRule.onNodeWithText(it.name).assertIsDisplayed()
            composeTestRule.onNodeWithText(it.email).assertIsDisplayed()
        }
    }

    @Test
    fun shouldObserveUserList() = runTest {
        // Given
        val user = User(123, "New", "new@user.com")
        composeTestRule.onNodeWithText(user.name).assertDoesNotExist()

        // When
        fakeUserRepositoryImpl.addUser(user)

        // Then
        composeTestRule.onNodeWithText(user.name).assertIsDisplayed()
    }

    @Test
    fun shouldShowAddUserDialog() {
        // When
        composeTestRule.onNodeWithContentDescription("Add a new user").performClick()

        // Then
        composeTestRule.onNodeWithText("Add new user").assertIsDisplayed()
    }

    @Test
    fun shouldShowRemoveUserDialog() {
        // Given
        val userName = fakeUserRepositoryImpl.userList.value.first().name

        // When
        composeTestRule.onNodeWithText(userName).performTouchInput { longClick() }

        // Then
        composeTestRule.onNodeWithText("Remove user").assertIsDisplayed()
    }
}
