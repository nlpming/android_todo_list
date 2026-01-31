package com.example.myapplication.presentation.todo

import com.example.myapplication.domain.model.Todo
import com.example.myapplication.domain.model.TodoCategory
import com.example.myapplication.domain.model.TodoFilter
import com.example.myapplication.domain.model.User

data class TodoState(
    val todos: List<Todo> = emptyList(),
    val currentFilter: TodoFilter = TodoFilter.ALL,
    val newTodoTitle: String = "",
    val selectedCategory: TodoCategory = TodoCategory.PERSONAL,
    val newTodoDueDate: Long? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showEditDialog: Boolean = false,
    val editingTodo: Todo? = null,
    val currentUserId: Long = 0,
    val currentUser: User? = null
) {
    val filteredTodos: List<Todo>
        get() = when (currentFilter) {
            TodoFilter.ALL -> todos
            TodoFilter.ACTIVE -> todos.filter { !it.isCompleted }
            TodoFilter.COMPLETED -> todos.filter { it.isCompleted }
        }

    val todayTasksCount: Int
        get() {
            val startOfDay = java.util.Calendar.getInstance().apply {
                set(java.util.Calendar.HOUR_OF_DAY, 0)
                set(java.util.Calendar.MINUTE, 0)
                set(java.util.Calendar.SECOND, 0)
                set(java.util.Calendar.MILLISECOND, 0)
            }.timeInMillis
            return todos.count { it.createdAt >= startOfDay }
        }

    val completedTasksCount: Int
        get() = todos.count { it.isCompleted }
}
