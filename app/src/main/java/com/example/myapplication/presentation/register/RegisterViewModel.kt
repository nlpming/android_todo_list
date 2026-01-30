package com.example.myapplication.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun onUsernameChange(username: String) {
        _state.update { it.copy(username = username, error = null) }
    }

    fun onDisplayNameChange(displayName: String) {
        _state.update { it.copy(displayName = displayName, error = null) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password, error = null) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _state.update { it.copy(confirmPassword = confirmPassword, error = null) }
    }

    fun onRegister(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = registerUserUseCase(
                username = _state.value.username,
                displayName = _state.value.displayName,
                password = _state.value.password,
                confirmPassword = _state.value.confirmPassword
            )

            result.fold(
                onSuccess = {
                    _state.update { it.copy(isLoading = false) }
                    onSuccess()
                },
                onFailure = { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Registration failed"
                        )
                    }
                }
            )
        }
    }
}
