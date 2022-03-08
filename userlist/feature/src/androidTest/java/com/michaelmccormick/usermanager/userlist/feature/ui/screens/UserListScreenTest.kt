package com.michaelmccormick.usermanager.userlist.feature.ui.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import com.michaelmccormick.usermanager.core.models.User
import com.michaelmccormick.usermanager.core.uitest.BaseUiTest
import com.michaelmccormick.usermanager.userlist.feature.viewmodels.UserListViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Test

internal class UserListScreenTest : BaseUiTest() {
    private val mockUserListViewModel: UserListViewModel = mockk(relaxed = true)

    @Test
    fun shouldShowAddUserButton() {
        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithContentDescription("Add a new user")
            .assertIsDisplayed()
            .assertIsEnabled()
            .assertHasClickAction()
    }

    @Test
    fun shouldShowLoading() {
        // Given
        every { mockUserListViewModel.loading } returns true

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithTag(USER_LIST_LOADING_TAG).assertIsDisplayed()
    }

    @Test
    fun shouldNotShowLoading() {
        // Given
        every { mockUserListViewModel.loading } returns false

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithTag(USER_LIST_LOADING_TAG).assertDoesNotExist()
    }

    @Test
    fun shouldShowUserList() {
        // Given
        every { mockUserListViewModel.userList } returns mutableStateListOf(
            User(1, "Mike", "mike@email.com"),
            User(2, "Gemma", "gemma@email.com"),
            User(3, "Lynda", "lynda@email.com"),
        )

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithText("Mike").assertIsDisplayed()
        composeTestRule.onNodeWithText("mike@email.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("Gemma").assertIsDisplayed()
        composeTestRule.onNodeWithText("gemma@email.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lynda").assertIsDisplayed()
        composeTestRule.onNodeWithText("lynda@email.com").assertIsDisplayed()
    }

    @Test
    fun shouldCallViewModelWhenUserListItemLongPressed() {
        // Given
        val user = User(1, "Mike", "mike@email.com")
        every { mockUserListViewModel.userList } returns mutableStateListOf(user)
        renderComposable()

        // When
        composeTestRule.onNodeWithText(user.name).performTouchInput { longClick() }

        // Then
        verify(exactly = 1) { mockUserListViewModel.onUserLongPressed(user) }
    }

    @Test
    fun shouldNotCallViewModelWhenUserListItemShortPressed() {
        // Given
        val user = User(1, "Mike", "mike@email.com")
        every { mockUserListViewModel.userList } returns mutableStateListOf(user)
        renderComposable()

        // When
        composeTestRule.onNodeWithText(user.name).performClick()

        // Then
        verify(exactly = 0) { mockUserListViewModel.onUserLongPressed(user) }
    }

    @Test
    fun shouldNavigateToAddUserWhenNavigationStateIsAddUser() {
        // Given
        every { mockUserListViewModel.navigationState } returns UserListViewModel.NavigationState.AddUser
        var onNavigateToAddUserCalled = false
        val onNavigateToAddUser = { onNavigateToAddUserCalled = true }

        // When
        renderComposable(onNavigateToAddUser = onNavigateToAddUser)

        // Then
        assertTrue(onNavigateToAddUserCalled)
        verify(exactly = 1) { mockUserListViewModel.onNavigationHandled() }
    }

    @Test
    fun shouldNavigateToRemoveUserWhenNavigationStateIsRemoveUser() {
        // Given
        val user = User(1, "Mike", "mike@email.com")
        every { mockUserListViewModel.navigationState } returns UserListViewModel.NavigationState.RemoveUser(user)
        var onNavigateToRemoveUserCalled = false
        var receivedUser: User? = null
        val onNavigateToRemoveUser = { passedUser: User ->
            onNavigateToRemoveUserCalled = true
            receivedUser = passedUser
        }

        // When
        renderComposable(onNavigateToRemoveUser = onNavigateToRemoveUser)

        // Then
        assertTrue(onNavigateToRemoveUserCalled)
        assertEquals(user, receivedUser)
        verify(exactly = 1) { mockUserListViewModel.onNavigationHandled() }
    }

    @Test
    fun shouldShowRetryWhenErrorStateIsFailure() {
        // Given
        every { mockUserListViewModel.errorState } returns UserListViewModel.ErrorState.GET_USER_LIST_FAILURE

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithText("Failed to get users, please check your connection and try again.")
            .assertIsDisplayed()
    }

    @Test
    fun shouldCallViewModelWhenRetryClicked() {
        // Given
        every { mockUserListViewModel.errorState } returns UserListViewModel.ErrorState.GET_USER_LIST_FAILURE
        renderComposable()

        // When
        composeTestRule.onNodeWithTag(USER_LIST_RETRY_BUTTON_TAG).performClick()

        // Then
        verify(exactly = 1) { mockUserListViewModel.onRetryUserListFetch() }
    }

    private fun renderComposable(
        onNavigateToAddUser: () -> Unit = {},
        onNavigateToRemoveUser: (User) -> Unit = {},
    ) {
        composeTestRule.setContent {
            UserListScreen(onNavigateToAddUser, onNavigateToRemoveUser, mockUserListViewModel)
        }
    }
}
