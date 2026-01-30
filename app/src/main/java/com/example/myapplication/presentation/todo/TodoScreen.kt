package com.example.myapplication.presentation.todo

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.presentation.todo.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    onLogout: () -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showMenu by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Menu"
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Logout") },
                            onClick = {
                                showMenu = false
                                viewModel.onLogout(onLogout)
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TodoHeader(
                todayTasksCount = state.todayTasksCount,
                completedTasksCount = state.completedTasksCount
            )

            TodoInput(
                value = state.newTodoTitle,
                onValueChange = viewModel::onNewTodoTitleChange,
                selectedCategory = state.selectedCategory,
                onCategoryChange = viewModel::onCategoryChange,
                onAddClick = viewModel::onAddTodo
            )

            TodoTabs(
                currentFilter = state.currentFilter,
                onFilterChange = viewModel::onFilterChange
            )

            TodoList(
                todos = state.filteredTodos,
                onToggleComplete = viewModel::onToggleTodo,
                onDelete = viewModel::onDeleteTodo,
                onTodoClick = viewModel::onEditTodo,
                modifier = Modifier.weight(1f)
            )
        }

        if (state.showEditDialog && state.editingTodo != null) {
            EditTodoDialog(
                todo = state.editingTodo!!,
                onDismiss = viewModel::onDismissEditDialog,
                onSave = viewModel::onUpdateTodo
            )
        }
    }
}
