package com.example.myapplication.presentation.register

data class RegisterState(
    val username: String = "",
    val displayName: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
