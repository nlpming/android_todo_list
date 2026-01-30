package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.TodoRepository
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(todoId: Long) {
        todoRepository.deleteTodo(todoId)
    }
}
