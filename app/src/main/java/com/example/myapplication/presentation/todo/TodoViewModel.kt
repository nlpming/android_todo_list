package com.example.myapplication.presentation.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.datastore.UserPreferences
import com.example.myapplication.domain.model.Todo
import com.example.myapplication.domain.model.TodoCategory
import com.example.myapplication.domain.model.TodoFilter
import com.example.myapplication.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val toggleTodoCompletionUseCase: ToggleTodoCompletionUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(TodoState())
    val state: StateFlow<TodoState> = _state.asStateFlow()

    init {
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        viewModelScope.launch {
            userPreferences.userId.collect { userId ->
                if (userId != null) {
                    _state.update { it.copy(currentUserId = userId) }

                    // Load complete user information
                    val user = getCurrentUserUseCase()
                    _state.update { it.copy(currentUser = user) }

                    loadTodos(userId)
                }
            }
        }
    }

    private fun loadTodos(userId: Long) {
        viewModelScope.launch {
            getTodosUseCase(userId).collect { todos ->
                _state.update { it.copy(todos = todos) }
            }
        }
    }

    fun onNewTodoTitleChange(title: String) {
        _state.update { it.copy(newTodoTitle = title) }
    }

    fun onCategoryChange(category: TodoCategory) {
        _state.update { it.copy(selectedCategory = category) }
    }

    fun onDueDateChange(dueDate: Long?) {
        _state.update { it.copy(newTodoDueDate = dueDate) }
    }

    fun onFilterChange(filter: TodoFilter) {
        _state.update { it.copy(currentFilter = filter) }
    }

    fun onAddTodo() {
        viewModelScope.launch {
            val newTodo = Todo(
                userId = _state.value.currentUserId,
                title = _state.value.newTodoTitle,
                category = _state.value.selectedCategory,
                dueDate = _state.value.newTodoDueDate
            )

            addTodoUseCase(newTodo).fold(
                onSuccess = {
                    _state.update { it.copy(newTodoTitle = "", newTodoDueDate = null) }
                },
                onFailure = { exception ->
                    _state.update { it.copy(error = exception.message) }
                }
            )
        }
    }

    fun onToggleTodo(todoId: Long) {
        viewModelScope.launch {
            toggleTodoCompletionUseCase(todoId)
        }
    }

    fun onDeleteTodo(todoId: Long) {
        viewModelScope.launch {
            deleteTodoUseCase(todoId)
        }
    }

    fun onEditTodo(todo: Todo) {
        _state.update {
            it.copy(
                showEditDialog = true,
                editingTodo = todo
            )
        }
    }

    fun onDismissEditDialog() {
        _state.update {
            it.copy(
                showEditDialog = false,
                editingTodo = null
            )
        }
    }

    fun onUpdateTodo(todo: Todo) {
        viewModelScope.launch {
            updateTodoUseCase(todo).fold(
                onSuccess = {
                    _state.update {
                        it.copy(
                            showEditDialog = false,
                            editingTodo = null
                        )
                    }
                },
                onFailure = { exception ->
                    _state.update { it.copy(error = exception.message) }
                }
            )
        }
    }

    fun onLogout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            logoutUseCase()
            onLogoutComplete()
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }

    fun onLanguageChange(languageCode: String, onLanguageChanged: () -> Unit) {
        viewModelScope.launch {
            userPreferences.saveLanguage(languageCode)
            onLanguageChanged()
        }
    }
}
