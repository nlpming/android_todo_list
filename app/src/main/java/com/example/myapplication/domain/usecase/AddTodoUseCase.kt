package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Todo
import com.example.myapplication.domain.repository.TodoRepository
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(todo: Todo): Result<Long> {
        return try {
            if (todo.title.isBlank()) {
                return Result.failure(Exception("Todo title cannot be empty"))
            }

            val todoId = todoRepository.insertTodo(todo)
            Result.success(todoId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
