package com.test.pwassignment.presentation.screens.login.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.pwassignment.auth.AuthManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> get() = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<LoginUiEvent>()
    val event = _event.asSharedFlow()

    fun onSchoolIdChange(value: String) {
        _uiState.update { it.copy(schoolId = value) }
    }

    fun onStudentIdChange(value: String) {
        _uiState.update { it.copy(studentId = value) }
    }

    fun onSignInClick() {
        val state = _uiState.value

        if (state.schoolId.isBlank() || state.studentId.isBlank()) {
            emitError("Please enter School ID and Student ID")
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        val email = "${state.schoolId}@pwassignment.com"
        val password = state.studentId

        AuthManager.login(
            email = email,
            password = password,
            onSuccess = {
                _uiState.update { it.copy(isLoading = false) }
                emitSuccess()
            },
            onError = { error ->
                _uiState.update { it.copy(isLoading = false) }
                emitError(error)
            }
        )
    }

    private fun emitError(message: String) {
        viewModelScope.launch {
            _event.emit(LoginUiEvent.Error(message))
        }
    }

    private fun emitSuccess() {
        viewModelScope.launch {
            _event.emit(LoginUiEvent.Success)
        }
    }
}


sealed class LoginUiEvent {
    data object Success : LoginUiEvent()
    data class Error(val message: String) : LoginUiEvent()
}


data class LoginUiState(
    val schoolId: String = "",
    val studentId: String = "",
    val isLoading: Boolean = false
)
