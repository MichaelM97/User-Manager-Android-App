package com.michaelmccormick.usermanager.userlist.feature.ui.dialogs

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.michaelmccormick.usermanager.core.extensions.showToast
import com.michaelmccormick.usermanager.core.ui.DialogComposable
import com.michaelmccormick.usermanager.userlist.feature.R
import com.michaelmccormick.usermanager.userlist.feature.viewmodels.RemoveUserViewModel

@Composable
internal fun RemoveUserDialog(
    userId: Int,
    userName: String,
    onCloseDialog: () -> Unit,
    removeUserViewModel: RemoveUserViewModel,
) {
    DialogComposable(
        onDismissRequest = removeUserViewModel::onCancel,
        onCancel = removeUserViewModel::onCancel,
        onConfirm = { removeUserViewModel.onRemoveUser(userId) },
        titleText = stringResource(R.string.remove_user_title),
        body = {
            Text(
                text = stringResource(R.string.remove_user_body, userName),
                style = MaterialTheme.typography.body1,
            )
        },
        loading = removeUserViewModel.loading,
    )
    when (removeUserViewModel.navigationState) {
        RemoveUserViewModel.NavigationState.CLOSE -> onCloseDialog()
        RemoveUserViewModel.NavigationState.CLOSE_AFTER_SUCCESS -> {
            LocalContext.current.showToast(R.string.remove_user_successful)
            onCloseDialog()
        }
        null -> {}
    }
    when (removeUserViewModel.errorState) {
        RemoveUserViewModel.ErrorState.REMOVE_USER_FAILURE -> {
            LocalContext.current.showToast(R.string.remove_user_failed)
        }
        null -> {}
    }
}
