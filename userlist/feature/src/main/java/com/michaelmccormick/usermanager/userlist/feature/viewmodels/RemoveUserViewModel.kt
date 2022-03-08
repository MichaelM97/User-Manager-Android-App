package com.michaelmccormick.usermanager.userlist.feature.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelmccormick.usermanager.userlist.domain.RemoveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
internal class RemoveUserViewModel @Inject constructor(
    private val removeUserUseCase: RemoveUserUseCase,
) : ViewModel() {
    var loading: Boolean by mutableStateOf(false)
        private set
    var navigationState: NavigationState? by mutableStateOf(null)
        private set
    var errorState: ErrorState? by mutableStateOf(null)
        private set

    fun onRemoveUser(userId: Int) {
        loading = true
        errorState = null
        viewModelScope.launch {
            removeUserUseCase.execute(userId)
                .onSuccess { navigationState = NavigationState.CLOSE_AFTER_SUCCESS }
                .onFailure {
                    errorState = ErrorState.REMOVE_USER_FAILURE
                    loading = false
                }
        }
    }

    fun onCancel() {
        navigationState = NavigationState.CLOSE
    }

    enum class NavigationState {
        CLOSE,
        CLOSE_AFTER_SUCCESS,
    }

    enum class ErrorState { REMOVE_USER_FAILURE }
}
