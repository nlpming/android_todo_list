package com.example.myapplication.domain.model

data class Todo(
    val id: Long = 0,
    val userId: Long,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val category: TodoCategory = TodoCategory.PERSONAL,
    val dueDate: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
