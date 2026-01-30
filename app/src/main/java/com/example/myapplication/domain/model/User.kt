package com.example.myapplication.domain.model

data class User(
    val id: Long = 0,
    val username: String,
    val displayName: String,
    val createdAt: Long = System.currentTimeMillis()
)
