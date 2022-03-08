package com.michaelmccormick.usermanager.userlist.feature.ui.dialogs

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.michaelmccormick.usermanager.core.models.Gender
import com.michaelmccormick.usermanager.core.ui.DIALOG_COMPOSABLE_CANCEL_BUTTON_TAG
import com.michaelmccormick.usermanager.core.ui.DIALOG_COMPOSABLE_CONFIRM_BUTTON_TAG
import com.michaelmccormick.usermanager.core.ui.DIALOG_COMPOSABLE_LOADING_TEST_TAG
import com.michaelmccormick.usermanager.core.uitest.BaseUiTest
import com.michaelmccormick.usermanager.core.uitest.assertIsError
import com.michaelmccormick.usermanager.core.uitest.assertIsNotError
import com.michaelmccormick.usermanager.userlist.feature.viewmodels.AddUserViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.assertTrue
import org.junit.Test

internal class AddUserDialogTest : BaseUiTest() {
    private val mockAddUserViewModel: AddUserViewModel = mockk(relaxed = true)

    @Test
    fun shouldShowTitle() {
        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithText("Add new user").assertIsDisplayed()
    }

    @Test
    fun shouldShowLoading() {
        // Given
        every { mockAddUserViewModel.loading } returns true

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithTag(DIALOG_COMPOSABLE_LOADING_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun shouldNotShowLoading() {
        // Given
        every { mockAddUserViewModel.loading } returns false

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithTag(DIALOG_COMPOSABLE_LOADING_TEST_TAG).assertDoesNotExist()
    }

    @Test
    fun shouldDisplayNameAndEmailInputFields() {
        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithText(NAME_FIELD_TEXT)
            .assertIsDisplayed()
            .assertIsEnabled()
            .assertIsNotError()
        composeTestRule.onNodeWithText(EMAIL_FIELD_TEXT)
            .assertIsDisplayed()
            .assertIsEnabled()
            .assertIsNotError()
    }

    @Test
    fun shouldDisplayNameAndEmailInputFieldsPopulated() {
        // Given
        val name = "Mike"
        every { mockAddUserViewModel.name } returns name
        val email = "mike@email.com"
        every { mockAddUserViewModel.email } returns email

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithText(name).assertIsDisplayed()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
    }

    @Test
    fun shouldCallViewModelWhenNameChanged() {
        // Given
        renderComposable()

        // When
        val nameInput = "John"
        composeTestRule.onNodeWithText(NAME_FIELD_TEXT).performTextInput(nameInput)

        // Then
        verify(exactly = 1) { mockAddUserViewModel.onNameChanged(nameInput) }
    }

    @Test
    fun shouldCallViewModelWhenEmailChanged() {
        // Given
        renderComposable()

        // When
        val emailInput = "John"
        composeTestRule.onNodeWithText(EMAIL_FIELD_TEXT).performTextInput(emailInput)

        // Then
        verify(exactly = 1) { mockAddUserViewModel.onEmailChanged(emailInput) }
    }

    @Test
    fun shouldDisableNameAndEmailInputFieldsWhenLoading() {
        // Given
        every { mockAddUserViewModel.loading } returns true

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithText(NAME_FIELD_TEXT).assertIsNotEnabled()
        composeTestRule.onNodeWithText(EMAIL_FIELD_TEXT).assertIsNotEnabled()
    }

    @Test
    fun shouldErrorNameInputFieldWhenEmptyNameError() {
        // Given
        every { mockAddUserViewModel.errorState } returns AddUserViewModel.ErrorState.EMPTY_NAME

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithText(NAME_FIELD_TEXT).assertIsError()
        composeTestRule.onNodeWithText(EMAIL_FIELD_TEXT).assertIsNotError()
    }

    @Test
    fun shouldErrorEmailInputFieldWhenEmptyEmailError() {
        // Given
        every { mockAddUserViewModel.errorState } returns AddUserViewModel.ErrorState.EMPTY_EMAIL

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithText(EMAIL_FIELD_TEXT).assertIsError()
        composeTestRule.onNodeWithText(NAME_FIELD_TEXT).assertIsNotError()
    }

    @Test
    fun shouldErrorEmailInputFieldWhenEmailInUseError() {
        // Given
        every { mockAddUserViewModel.errorState } returns AddUserViewModel.ErrorState.EMAIL_IN_USE

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithText(EMAIL_FIELD_TEXT).assertIsError()
        composeTestRule.onNodeWithText(NAME_FIELD_TEXT).assertIsNotError()
    }

    @Test
    fun shouldErrorEmailInputFieldWhenInvalidEmailError() {
        // Given
        every { mockAddUserViewModel.errorState } returns AddUserViewModel.ErrorState.INVALID_EMAIL

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithText(EMAIL_FIELD_TEXT).assertIsError()
        composeTestRule.onNodeWithText(NAME_FIELD_TEXT).assertIsNotError()
    }

    @Test
    fun shouldDisplayMaleAndFemaleGenderOptions() {
        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithTag(MALE_RADIO_BUTTON_TEST_TAG)
            .assertIsDisplayed()
            .assertIsEnabled()
        composeTestRule.onNodeWithTag(FEMALE_RADIO_BUTTON_TEST_TAG)
            .assertIsDisplayed()
            .assertIsEnabled()
        composeTestRule.onNodeWithText(MALE_GENDER_OPTION_TEXT)
            .assertIsDisplayed()
            .assertHasClickAction()
        composeTestRule.onNodeWithText(FEMALE_GENDER_OPTION_TEXT)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun shouldDisplayMaleGenderAsSelected() {
        // Given
        every { mockAddUserViewModel.gender } returns Gender.MALE

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithTag(MALE_RADIO_BUTTON_TEST_TAG)
            .assertIsDisplayed()
            .assertIsEnabled()
            .assertIsSelected()
        composeTestRule.onNodeWithTag(FEMALE_RADIO_BUTTON_TEST_TAG)
            .assertIsDisplayed()
            .assertIsEnabled()
            .assertIsNotSelected()
    }

    @Test
    fun shouldDisplayFemaleGenderAsSelected() {
        // Given
        every { mockAddUserViewModel.gender } returns Gender.FEMALE

        // When
        renderComposable()

        // Then
        composeTestRule.onNodeWithTag(FEMALE_RADIO_BUTTON_TEST_TAG)
            .assertIsDisplayed()
            .assertIsEnabled()
            .assertIsSelected()
        composeTestRule.onNodeWithTag(MALE_RADIO_BUTTON_TEST_TAG)
            .assertIsDisplayed()
            .assertIsEnabled()
            .assertIsNotSelected()
    }

    @Test
    fun shouldCallViewModelWhenMaleButtonPressed() {
        // Given
        renderComposable()

        // When
        composeTestRule.onNodeWithTag(MALE_RADIO_BUTTON_TEST_TAG).performClick()

        // Then
        verify(exactly = 1) { mockAddUserViewModel.onGenderSelected(Gender.MALE) }
    }

    @Test
    fun shouldCallViewModelWhenMaleTextPressed() {
        // Given
        renderComposable()

        // When
        composeTestRule.onNodeWithText(MALE_GENDER_OPTION_TEXT).performClick()

        // Then
        verify(exactly = 1) { mockAddUserViewModel.onGenderSelected(Gender.MALE) }
    }

    @Test
    fun shouldCallViewModelWhenFemaleButtonPressed() {
        // Given
        renderComposable()

        // When
        composeTestRule.onNodeWithTag(FEMALE_RADIO_BUTTON_TEST_TAG).performClick()

        // Then
        verify(exactly = 1) { mockAddUserViewModel.onGenderSelected(Gender.FEMALE) }
    }

    @Test
    fun shouldCallViewModelWhenFemaleTextPressed() {
        // Given
        renderComposable()

        // When
        composeTestRule.onNodeWithText(FEMALE_GENDER_OPTION_TEXT).performClick()

        // Then
        verify(exactly = 1) { mockAddUserViewModel.onGenderSelected(Gender.FEMALE) }
    }

    @Test
    fun shouldCallViewModelWhenConfirmPressed() {
        // Given
        renderComposable()

        // When
        composeTestRule.onNodeWithTag(DIALOG_COMPOSABLE_CONFIRM_BUTTON_TAG).performClick()

        // Then
        verify(exactly = 1) { mockAddUserViewModel.onAddUser() }
    }

    @Test
    fun shouldCallViewModelWhenCancelPressed() {
        // Given
        renderComposable()

        // When
        composeTestRule.onNodeWithTag(DIALOG_COMPOSABLE_CANCEL_BUTTON_TAG).performClick()

        // Then
        verify(exactly = 1) { mockAddUserViewModel.onCancel() }
    }

    @Test
    fun shouldCloseDialogWhenNavigationStateIsClose() {
        // Given
        every { mockAddUserViewModel.navigationState } returns AddUserViewModel.NavigationState.CLOSE
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
        every { mockAddUserViewModel.navigationState } returns AddUserViewModel.NavigationState.CLOSE_AFTER_SUCCESS
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
        composeTestRule.setContent { AddUserDialog(onCloseDialog, mockAddUserViewModel) }
    }

    private companion object {
        const val NAME_FIELD_TEXT = "Enter users name"
        const val EMAIL_FIELD_TEXT = "Enter users email"
        const val MALE_GENDER_OPTION_TEXT = "Male"
        const val FEMALE_GENDER_OPTION_TEXT = "Female"
    }
}
