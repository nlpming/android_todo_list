package com.example.myapplication.presentation.todo

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.R
import com.example.myapplication.presentation.todo.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    onLogout: () -> Unit,
    onLanguageChange: () -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showMenu by remember { mutableStateOf(false) }
    var showLanguageMenu by remember { mutableStateOf(false) }
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
                title = {
                    state.currentUser?.let { user ->
                        Text(
                            text = stringResource(R.string.welcome_user, user.displayName),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.menu)
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.language)) },
                            onClick = { showLanguageMenu = true }
                        )

                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.logout)) },
                            onClick = {
                                showMenu = false
                                viewModel.onLogout(onLogout)
                            }
                        )
                    }

                    // Language submenu
                    DropdownMenu(
                        expanded = showLanguageMenu,
                        onDismissRequest = { showLanguageMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.chinese)) },
                            onClick = {
                                showLanguageMenu = false
                                showMenu = false
                                viewModel.onLanguageChange("zh", onLanguageChange)
                            }
                        )

                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.english)) },
                            onClick = {
                                showLanguageMenu = false
                                showMenu = false
                                viewModel.onLanguageChange("en", onLanguageChange)
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
                dueDate = state.newTodoDueDate,
                onDueDateChange = viewModel::onDueDateChange,
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
