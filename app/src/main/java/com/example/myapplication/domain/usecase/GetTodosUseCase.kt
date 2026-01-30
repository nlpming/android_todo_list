package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Todo
import com.example.myapplication.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodosUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(userId: Long): Flow<List<Todo>> {
        return todoRepository.getTodosByUserId(userId)
    }
}
