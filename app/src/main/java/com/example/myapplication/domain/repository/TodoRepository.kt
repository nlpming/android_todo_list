package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodosByUserId(userId: Long): Flow<List<Todo>>
    suspend fun getTodoById(todoId: Long): Todo?
    suspend fun insertTodo(todo: Todo): Long
    suspend fun updateTodo(todo: Todo)
    suspend fun deleteTodo(todoId: Long)
    suspend fun toggleTodoCompletion(todoId: Long)
}
