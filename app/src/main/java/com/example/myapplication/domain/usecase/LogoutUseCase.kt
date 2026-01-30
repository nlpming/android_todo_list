package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() {
        userRepository.clearCurrentUserId()
    }
}
