package com.example.myapplication.presentation.todo.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.model.TodoCategory

@Composable
fun TodoInput(
    value: String,
    onValueChange: (String) -> Unit,
    selectedCategory: TodoCategory,
    onCategoryChange: (TodoCategory) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showCategoryMenu by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    placeholder = { Text("Add a new task...") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        if (value.isNotBlank()) {
                            onAddClick()
                        }
                    },
                    enabled = value.isNotBlank()
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add task"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Category selector
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Category:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box {
                    TextButton(onClick = { showCategoryMenu = true }) {
                        CategoryTag(category = selectedCategory)
                    }

                    DropdownMenu(
                        expanded = showCategoryMenu,
                        onDismissRequest = { showCategoryMenu = false }
                    ) {
                        TodoCategory.entries.forEach { category ->
                            DropdownMenuItem(
                                text = { CategoryTag(category = category) },
                                onClick = {
                                    onCategoryChange(category)
                                    showCategoryMenu = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
