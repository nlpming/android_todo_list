package com.example.myapplication.presentation.todo.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.domain.model.TodoFilter

@Composable
fun TodoTabs(
    currentFilter: TodoFilter,
    onFilterChange: (TodoFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        selectedTabIndex = currentFilter.ordinal,
        modifier = modifier
    ) {
        TodoFilter.entries.forEach { filter ->
            Tab(
                selected = currentFilter == filter,
                onClick = { onFilterChange(filter) },
                text = {
                    Text(
                        text = when (filter) {
                            TodoFilter.ALL -> stringResource(R.string.tab_all)
                            TodoFilter.ACTIVE -> stringResource(R.string.tab_active)
                            TodoFilter.COMPLETED -> stringResource(R.string.tab_completed)
                        },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            )
        }
    }
}
