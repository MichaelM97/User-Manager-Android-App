package com.michaelmccormick.usermanager.core.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.michaelmccormick.usermanager.core.uitest.BaseUiTest
import org.junit.Test

internal class DialogComposableTest : BaseUiTest() {
    @Test
    fun shouldUsePassedTitle() {
        // Given
        val title = "My dialog"

        // When
        renderComposable(titleText = title)

        // Then
        composeTestRule.onNodeWithText(title).assertIsDisplayed()
    }

    @Test
    fun shouldUsePassedBody() {
        // Given
        val body = @Composable { Text("Example body") }

        // When
        renderComposable(body = body)

        // Then
        composeTestRule.onNodeWithText("Example body").assertIsDisplayed()
    }

    @Test
    fun shouldShowLoadingAndHideButtons() {
        // When
        renderComposable(loading = true)

        // Then
        composeTestRule.onNodeWithTag(DIALOG_COMPOSABLE_LOADING_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DIALOG_COMPOSABLE_CANCEL_BUTTON_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(DIALOG_COMPOSABLE_CONFIRM_BUTTON_TAG).assertDoesNotExist()
    }

    @Test
    fun shouldShowButtonsAndHideLoading() {
        // When
        renderComposable(loading = false)

        // Then
        composeTestRule.onNodeWithTag(DIALOG_COMPOSABLE_CANCEL_BUTTON_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DIALOG_COMPOSABLE_CONFIRM_BUTTON_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DIALOG_COMPOSABLE_LOADING_TEST_TAG).assertDoesNotExist()
    }

    private fun renderComposable(
        onDismissRequest: () -> Unit = {},
        onCancel: () -> Unit = {},
        onConfirm: () -> Unit = {},
        titleText: String = "",
        body: (@Composable () -> Unit)? = null,
        loading: Boolean = false,
    ) {
        composeTestRule.setContent {
            DialogComposable(
                onDismissRequest = onDismissRequest,
                onCancel = onCancel,
                onConfirm = onConfirm,
                titleText = titleText,
                body = body,
                loading = loading,
            )
        }
    }
}
