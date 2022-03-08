package com.michaelmccormick.usermanager.userlist.feature.ui.dialogs

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.michaelmccormick.usermanager.core.ui.DIALOG_COMPOSABLE_CANCEL_BUTTON_TAG
import com.michaelmccormick.usermanager.core.ui.DIALOG_COMPOSABLE_CONFIRM_BUTTON_TAG
import com.michaelmccormick.usermanager.core.ui.DIALOG_COMPOSABLE_LOADING_TEST_TAG
import com.michaelmccormick.usermanager.core.uitest.BaseUiTest
import com.michaelmccormick.usermanager.userlist.feature.viewmodels.RemoveUserViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.assertTrue
import org.junit.Test

internal class RemoveUserDialogTest : BaseUiTest() {
    private val mockRemoveUserViewModel: RemoveUserViewModel = mockk(relaxed = true)

    @Test
    fun shouldShowTitle() {
        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithText("Remove user").assertIsDisplayed()
    }

    @Test
    fun shouldShowBody() {
        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithText("Are you sure that you want to remove $USER_NAME?").assertIsDisplayed()
    }

    @Test
    fun shouldShowLoading() {
        // Given
        every { mockRemoveUserViewModel.loading } returns true

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithTag(DIALOG_COMPOSABLE_LOADING_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun shouldNotShowLoading() {
        // Given
        every { mockRemoveUserViewModel.loading } returns false

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithTag(DIALOG_COMPOSABLE_LOADING_TEST_TAG).assertDoesNotExist()
    }

    @Test
    fun shouldCallViewModelWhenConfirmPressed() {
        // Given
        renderComposable()

        // When
        composeTestRule.onNodeWithTag(DIALOG_COMPOSABLE_CONFIRM_BUTTON_TAG).performClick()

        // Then
        verify(exactly = 1) { mockRemoveUserViewModel.onRemoveUser(USER_ID) }
    }

    @Test
    fun shouldCallViewModelWhenCancelPressed() {
        // Given
        renderComposable()

        // When
        composeTestRule.onNodeWithTag(DIALOG_COMPOSABLE_CANCEL_BUTTON_TAG).performClick()

        // Then
        verify(exactly = 1) { mockRemoveUserViewModel.onCancel() }
    }

    @Test
    fun shouldCloseDialogWhenNavigationStateIsClose() {
        // Given
        every { mockRemoveUserViewModel.navigationState } returns RemoveUserViewModel.NavigationState.CLOSE
        var onCloseCalled = false
        val onClose = { onCloseCalled = true }

        // When
        renderComposable(onCloseDialog = onClose)

        // Then
        assertTrue(onCloseCalled)
    }

    @Test
    fun shouldCloseDialogWhenNavigationStateIsCloseAfterSuccess() {
        // Given
        every { mockRemoveUserViewModel.navigationState } returns RemoveUserViewModel.NavigationState.CLOSE_AFTER_SUCCESS
        var onCloseCalled = false
        val onClose = { onCloseCalled = true }

        // When
        renderComposable(onCloseDialog = onClose)

        // Then
        assertTrue(onCloseCalled)
    }

    private fun renderComposable(
        onCloseDialog: () -> Unit = {},
    ) {
        composeTestRule.setContent {
            RemoveUserDialog(USER_ID, USER_NAME, onCloseDialog, mockRemoveUserViewModel)
        }
    }

    private companion object {
        const val USER_ID = 123
        const val USER_NAME = "Mike"
    }
}
