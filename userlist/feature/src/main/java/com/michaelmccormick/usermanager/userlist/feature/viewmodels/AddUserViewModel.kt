package com.michaelmccormick.usermanager.userlist.feature.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelmccormick.usermanager.core.models.Gender
import com.michaelmccormick.usermanager.core.models.errors.CreateUserError
import com.michaelmccormick.usermanager.userlist.domain.AddUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
internal class AddUserViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
) : ViewModel() {
    var name: String by mutableStateOf("")
        private set
    var email: String by mutableStateOf("")
        private set
    var gender: Gender by mutableStateOf(Gender.MALE)
        private set
    var loading: Boolean by mutableStateOf(false)
        private set
    var navigationState: NavigationState? by mutableStateOf(null)
        private set
    var errorState: ErrorState? by mutableStateOf(null)
        private set

    fun onNameChanged(name: String) {
        this.name = name
        resetErrorState()
    }

    fun onEmailChanged(email: String) {
        this.email = email
        resetErrorState()
    }

    fun onGenderSelected(gender: Gender) {
        this.gender = gender
    }

    fun onAddUser() {
        if (name.isBlank()) {
            errorState = ErrorState.EMPTY_NAME
            return
        }
        if (email.isBlank()) {
            errorState = ErrorState.EMPTY_EMAIL
            return
        }
        viewModelScope.launch {
            loading = true
            addUserUseCase.execute(name, email, gender)
                .onSuccess { navigationState = NavigationState.CLOSE_AFTER_SUCCESS }
                .onFailure {
                    val error = when (it) {
                        is CreateUserError.EmailInUse -> ErrorState.EMAIL_IN_USE
                        is CreateUserError.InvalidEmail -> ErrorState.INVALID_EMAIL
                        else -> ErrorState.ADD_USER_FAILURE
                    }
                    errorState = error
                    loading = false
                }
        }
    }

    fun onCancel() {
        resetErrorState()
        navigationState = NavigationState.CLOSE
    }

    private fun resetErrorState() {
        errorState = null
    }

    enum class NavigationState {
        CLOSE,
        CLOSE_AFTER_SUCCESS,
    }

    enum class ErrorState {
        EMPTY_NAME,
        EMPTY_EMAIL,
        ADD_USER_FAILURE,
        EMAIL_IN_USE,
        INVALID_EMAIL,
    }
}
