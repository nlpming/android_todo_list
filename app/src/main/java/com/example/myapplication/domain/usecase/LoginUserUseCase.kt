package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<User> {
        if (username.isBlank()) {
            return Result.failure(Exception("Username cannot be empty"))
        }

        if (password.isBlank()) {
            return Result.failure(Exception("Password cannot be empty"))
        }

        return userRepository.login(username, password)
    }
}
