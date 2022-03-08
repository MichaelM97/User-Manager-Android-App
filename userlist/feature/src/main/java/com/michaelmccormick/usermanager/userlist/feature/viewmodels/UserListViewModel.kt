package com.michaelmccormick.usermanager.userlist.feature.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelmccormick.usermanager.core.models.User
import com.michaelmccormick.usermanager.userlist.domain.GetUserListUseCase
import com.michaelmccormick.usermanager.userlist.domain.RefreshUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
internal class UserListViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase,
    private val refreshUserListUseCase: RefreshUserListUseCase,
) : ViewModel() {
    var loading: Boolean by mutableStateOf(true)
        private set
    val userList: SnapshotStateList<User> = mutableStateListOf()
    var navigationState: NavigationState? by mutableStateOf(null)
        private set
    var errorState: ErrorState? by mutableStateOf(null)
        private set

    init {
        viewModelScope.launch {
            getUserListUseCase.execute().collect {
                userList.clear()
                userList.addAll(it)
                loading = false
            }
        }
        refreshUserList()
    }

    fun onRetryUserListFetch() {
        errorState = null
        refreshUserList()
    }

    private fun refreshUserList() {
        loading = true
        viewModelScope.launch {
            refreshUserListUseCase.execute()
                .onFailure {
                    errorState = ErrorState.GET_USER_LIST_FAILURE
                    loading = false
                }
        }
    }

    fun onAddUserSelected() {
        navigationState = NavigationState.AddUser
    }

    fun onUserLongPressed(user: User) {
        navigationState = NavigationState.RemoveUser(user)
    }

    fun onNavigationHandled() {
        navigationState = null
    }

    sealed class NavigationState {
        object AddUser : NavigationState()
        data class RemoveUser(val user: User) : NavigationState()
    }

    enum class ErrorState { GET_USER_LIST_FAILURE }
}
