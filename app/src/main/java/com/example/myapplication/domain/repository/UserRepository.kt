package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.User

interface UserRepository {
    suspend fun register(username: String, displayName: String, password: String): Result<User>
    suspend fun login(username: String, password: String): Result<User>
    suspend fun getUserById(userId: Long): User?
    suspend fun saveCurrentUserId(userId: Long)
    suspend fun clearCurrentUserId()
}
