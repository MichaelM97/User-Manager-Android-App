package com.michaelmccormick.usermanager.userlist.feature.ui.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.michaelmccormick.usermanager.core.extensions.showToast
import com.michaelmccormick.usermanager.core.models.Gender
import com.michaelmccormick.usermanager.core.ui.DialogComposable
import com.michaelmccormick.usermanager.userlist.feature.R
import com.michaelmccormick.usermanager.userlist.feature.viewmodels.AddUserViewModel

@Composable
internal fun AddUserDialog(
    onCloseDialog: () -> Unit,
    addUserViewModel: AddUserViewModel,
) {
    DialogComposable(
        onDismissRequest = addUserViewModel::onCancel,
        onCancel = addUserViewModel::onCancel,
        onConfirm = addUserViewModel::onAddUser,
        titleText = stringResource(R.string.add_user_title),
        body = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    modifier = Modifier.padding(vertical = 12.dp),
                    value = addUserViewModel.name,
                    onValueChange = addUserViewModel::onNameChanged,
                    placeholder = { Text(text = stringResource(R.string.name_field_label)) },
                    enabled = !addUserViewModel.loading,
                    isError = addUserViewModel.errorState == AddUserViewModel.ErrorState.EMPTY_NAME,
                )
                OutlinedTextField(
                    value = addUserViewModel.email,
                    onValueChange = addUserViewModel::onEmailChanged,
                    placeholder = { Text(text = stringResource(R.string.email_field_label)) },
                    enabled = !addUserViewModel.loading,
                    isError = addUserViewModel.errorState == AddUserViewModel.ErrorState.EMPTY_EMAIL ||
                            addUserViewModel.errorState == AddUserViewModel.ErrorState.EMAIL_IN_USE ||
                            addUserViewModel.errorState == AddUserViewModel.ErrorState.INVALID_EMAIL,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        RadioButton(
                            selected = addUserViewModel.gender == Gender.MALE,
                            onClick = { addUserViewModel.onGenderSelected(Gender.MALE) },
                            modifier = Modifier.testTag(MALE_RADIO_BUTTON_TEST_TAG),
                        )
                        Text(
                            text = stringResource(R.string.male),
                            modifier = Modifier.clickable(onClick = { addUserViewModel.onGenderSelected(Gender.MALE) }),
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        RadioButton(
                            selected = addUserViewModel.gender == Gender.FEMALE,
                            onClick = { addUserViewModel.onGenderSelected(Gender.FEMALE) },
                            modifier = Modifier.testTag(FEMALE_RADIO_BUTTON_TEST_TAG),
                        )
                        Text(
                            text = stringResource(R.string.female),
                            modifier = Modifier.clickable(onClick = { addUserViewModel.onGenderSelected(Gender.FEMALE) }),
                        )
                    }
                }
            }
        },
        loading = addUserViewModel.loading,
    )
    when (addUserViewModel.navigationState) {
        AddUserViewModel.NavigationState.CLOSE -> onCloseDialog()
        AddUserViewModel.NavigationState.CLOSE_AFTER_SUCCESS -> {
            LocalContext.current.showToast(R.string.add_user_successful)
            onCloseDialog()
        }
        null -> {}
    }
    when (addUserViewModel.errorState) {
        AddUserViewModel.ErrorState.EMPTY_NAME -> {
            LocalContext.current.showToast(R.string.empty_name_error)
        }
        AddUserViewModel.ErrorState.EMPTY_EMAIL -> {
            LocalContext.current.showToast(R.string.empty_email_error)
        }
        AddUserViewModel.ErrorState.ADD_USER_FAILURE -> {
            LocalContext.current.showToast(R.string.add_user_failed)
        }
        AddUserViewModel.ErrorState.EMAIL_IN_USE -> {
            LocalContext.current.showToast(R.string.add_user_failed_email_in_use)
        }
        AddUserViewModel.ErrorState.INVALID_EMAIL -> {
            LocalContext.current.showToast(R.string.add_user_failed_invalid_email)
        }
        null -> {}
    }
}

internal const val MALE_RADIO_BUTTON_TEST_TAG = "radioButtonMale"
internal const val FEMALE_RADIO_BUTTON_TEST_TAG = "radioButtonFemale"
