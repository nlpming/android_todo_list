package com.example.myapplication.data.repository

import com.example.myapplication.data.local.dao.TodoDao
import com.example.myapplication.data.local.entity.TodoEntity
import com.example.myapplication.domain.model.Todo
import com.example.myapplication.domain.model.TodoCategory
import com.example.myapplication.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {

    override fun getTodosByUserId(userId: Long): Flow<List<Todo>> {
        return todoDao.getTodosByUserId(userId).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getTodoById(todoId: Long): Todo? {
        return todoDao.getTodoById(todoId)?.toDomainModel()
    }

    override suspend fun insertTodo(todo: Todo): Long {
        val entity = todo.toEntity()
        return todoDao.insert(entity)
    }

    override suspend fun updateTodo(todo: Todo) {
        val entity = todo.toEntity()
        todoDao.update(entity)
    }

    override suspend fun deleteTodo(todoId: Long) {
        todoDao.deleteById(todoId)
    }

    override suspend fun toggleTodoCompletion(todoId: Long) {
        val todoEntity = todoDao.getTodoById(todoId) ?: return
        val updatedEntity = todoEntity.copy(
            isCompleted = !todoEntity.isCompleted,
            updatedAt = System.currentTimeMillis()
        )
        todoDao.update(updatedEntity)
    }

    private fun TodoEntity.toDomainModel(): Todo {
        return Todo(
            id = id,
            userId = userId,
            title = title,
            description = description,
            isCompleted = isCompleted,
            category = TodoCategory.valueOf(category),
            dueDate = dueDate,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private fun Todo.toEntity(): TodoEntity {
        return TodoEntity(
            id = id,
            userId = userId,
            title = title,
            description = description,
            isCompleted = isCompleted,
            category = category.name,
            dueDate = dueDate,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
