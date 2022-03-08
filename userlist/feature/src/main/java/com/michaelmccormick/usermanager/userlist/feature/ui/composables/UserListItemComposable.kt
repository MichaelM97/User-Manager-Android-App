package com.michaelmccormick.usermanager.userlist.feature.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.michaelmccormick.usermanager.core.models.User

@Composable
internal fun UserListItemComposable(
    user: User,
    modifier: Modifier,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Column {
            Text(
                text = user.name,
                style = MaterialTheme.typography.h6,
            )
            Text(
                text = user.email,
                style = MaterialTheme.typography.body1,
            )
        }
    }
}
