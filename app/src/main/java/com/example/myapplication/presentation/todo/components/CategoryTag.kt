package com.example.myapplication.presentation.todo.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.model.TodoCategory

@Composable
fun CategoryTag(
    category: TodoCategory,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = category.color.copy(alpha = 0.2f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = category.displayName,
            style = MaterialTheme.typography.labelSmall,
            color = category.color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
