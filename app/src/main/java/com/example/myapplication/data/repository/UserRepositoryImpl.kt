package com.example.myapplication.data.repository

import com.example.myapplication.data.local.dao.UserDao
import com.example.myapplication.data.local.datastore.UserPreferences
import com.example.myapplication.data.local.entity.UserEntity
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.UserRepository
import java.security.MessageDigest
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userPreferences: UserPreferences
) : UserRepository {

    override suspend fun register(username: String, displayName: String, password: String): Result<User> {
        return try {
            // Check if username already exists
            val existingUser = userDao.getUserByUsername(username)
            if (existingUser != null) {
                return Result.failure(Exception("Username already exists"))
            }

            val passwordHash = hashPassword(password)
            val userEntity = UserEntity(
                username = username,
                displayName = displayName,
                passwordHash = passwordHash
            )

            val userId = userDao.insert(userEntity)
            val user = User(
                id = userId,
                username = username,
                displayName = displayName,
                createdAt = userEntity.createdAt
            )

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(username: String, password: String): Result<User> {
        return try {
            val userEntity = userDao.getUserByUsername(username)
                ?: return Result.failure(Exception("User not found"))

            val passwordHash = hashPassword(password)
            if (userEntity.passwordHash != passwordHash) {
                return Result.failure(Exception("Invalid password"))
            }

            val user = User(
                id = userEntity.id,
                username = userEntity.username,
                displayName = userEntity.displayName,
                createdAt = userEntity.createdAt
            )

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserById(userId: Long): User? {
        val userEntity = userDao.getUserById(userId) ?: return null
        return User(
            id = userEntity.id,
            username = userEntity.username,
            displayName = userEntity.displayName,
            createdAt = userEntity.createdAt
        )
    }

    override suspend fun saveCurrentUserId(userId: Long) {
        userPreferences.saveUserId(userId)
    }

    override suspend fun clearCurrentUserId() {
        userPreferences.clearUserId()
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
