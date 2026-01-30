package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, displayName: String, password: String, confirmPassword: String): Result<User> {
        // Validation
        if (username.isBlank() || username.length < 3) {
            return Result.failure(Exception("Username must be at least 3 characters"))
        }

        if (!username.matches(Regex("^[a-zA-Z0-9_]+$"))) {
            return Result.failure(Exception("Username must be alphanumeric"))
        }

        if (displayName.isBlank()) {
            return Result.failure(Exception("Display name cannot be empty"))
        }

        if (password.length < 6) {
            return Result.failure(Exception("Password must be at least 6 characters"))
        }

        if (password != confirmPassword) {
            return Result.failure(Exception("Passwords do not match"))
        }

        return userRepository.register(username, displayName, password)
    }
}
