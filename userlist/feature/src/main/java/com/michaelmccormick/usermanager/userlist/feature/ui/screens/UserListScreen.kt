package com.michaelmccormick.usermanager.userlist.feature.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.michaelmccormick.usermanager.core.models.User
import com.michaelmccormick.usermanager.core.ui.longPressClickableWithRipple
import com.michaelmccormick.usermanager.userlist.feature.R
import com.michaelmccormick.usermanager.userlist.feature.ui.composables.UserListItemComposable
import com.michaelmccormick.usermanager.userlist.feature.viewmodels.UserListViewModel

@Composable
internal fun UserListScreen(
    onNavigateToAddUser: () -> Unit,
    onNavigateToRemoveUser: (User) -> Unit,
    userListViewModel: UserListViewModel,
) {
    Scaffold(
        floatingActionButton = { AddUserFloatingActionButton(onClick = userListViewModel::onAddUserSelected) },
    ) {
        if (userListViewModel.loading) {
            LoadingSpinner()
        }
        if (userListViewModel.userList.isNotEmpty()) {
            UserList(
                userList = userListViewModel.userList,
                onItemLongPressed = userListViewModel::onUserLongPressed,
            )
        }
        when (val navState = userListViewModel.navigationState) {
            is UserListViewModel.NavigationState.AddUser -> {
                onNavigateToAddUser()
                userListViewModel.onNavigationHandled()
            }
            is UserListViewModel.NavigationState.RemoveUser -> {
                onNavigateToRemoveUser(navState.user)
                userListViewModel.onNavigationHandled()
            }
            null -> {}
        }
        when (userListViewModel.errorState) {
            UserListViewModel.ErrorState.GET_USER_LIST_FAILURE -> {
                RetryFetch(onRetryUserListFetch = userListViewModel::onRetryUserListFetch)
            }
            null -> {}
        }
    }
}

@Composable
private fun AddUserFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_user_button_content_description),
        )
    }
}

@Composable
private fun LoadingSpinner() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag(USER_LIST_LOADING_TAG),
        )
    }
}

@Composable
private fun UserList(
    userList: List<User>,
    onItemLongPressed: (User) -> Unit,
) {
    LazyColumn {
        items(userList, key = { it.id }) { user ->
            UserListItemComposable(
                user = user,
                modifier = Modifier
                    .longPressClickableWithRipple(onLongPress = { onItemLongPressed(user) })
                    .padding(all = 12.dp),
            )
            Divider()
        }
    }
}

@Composable
private fun RetryFetch(
    onRetryUserListFetch: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.user_list_fetch_failed),
                textAlign = TextAlign.Center,
            )
            IconButton(
                onClick = onRetryUserListFetch,
                modifier = Modifier.testTag(USER_LIST_RETRY_BUTTON_TAG),
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }
        }
    }
}

internal const val USER_LIST_LOADING_TAG = "userListLoadingIndicator"
internal const val USER_LIST_RETRY_BUTTON_TAG = "userListRetryButton"
