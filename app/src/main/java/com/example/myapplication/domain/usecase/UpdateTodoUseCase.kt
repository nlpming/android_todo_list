package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Todo
import com.example.myapplication.domain.repository.TodoRepository
import javax.inject.Inject

class UpdateTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(todo: Todo): Result<Unit> {
        return try {
            if (todo.title.isBlank()) {
                return Result.failure(Exception("Todo title cannot be empty"))
            }

            val updatedTodo = todo.copy(updatedAt = System.currentTimeMillis())
            todoRepository.updateTodo(updatedTodo)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
