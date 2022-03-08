package com.michaelmccormick.usermanager.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource

@Composable
fun DialogComposable(
    onDismissRequest: () -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    titleText: String,
    body: (@Composable () -> Unit)? = null,
    loading: Boolean = false,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        buttons = {
            if (loading) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag(DIALOG_COMPOSABLE_LOADING_TEST_TAG),
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    TextButton(
                        onClick = onCancel,
                        modifier = Modifier.testTag(DIALOG_COMPOSABLE_CANCEL_BUTTON_TAG),
                    ) {
                        Text(text = stringResource(R.string.cancel))
                    }
                    TextButton(
                        onClick = onConfirm,
                        modifier = Modifier.testTag(DIALOG_COMPOSABLE_CONFIRM_BUTTON_TAG),
                    ) {
                        Text(text = stringResource(R.string.confirm))
                    }
                }
            }
        },
        title = {
            Text(
                text = titleText,
                style = MaterialTheme.typography.h6,
            )
        },
        text = body,
    )
}

const val DIALOG_COMPOSABLE_LOADING_TEST_TAG = "dialogComposableLoadingIndicator"
const val DIALOG_COMPOSABLE_CONFIRM_BUTTON_TAG = "dialogComposableConfirmButton"
const val DIALOG_COMPOSABLE_CANCEL_BUTTON_TAG = "dialogComposableCancelButton"
