package com.example.myapplication.domain.usecase

import com.example.myapplication.data.local.datastore.UserPreferences
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
) {
    suspend operator fun invoke(): User? {
        val userId = userPreferences.userId.first() ?: return null
        return userRepository.getUserById(userId)
    }
}
