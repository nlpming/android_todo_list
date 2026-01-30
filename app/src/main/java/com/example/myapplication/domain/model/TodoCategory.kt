package com.example.myapplication.domain.model

import androidx.compose.ui.graphics.Color

enum class TodoCategory(val displayName: String, val color: Color) {
    WORK("Work", Color(0xFF667EEA)),
    PERSONAL("Personal", Color(0xFF764BA2)),
    HEALTH("Health", Color(0xFF06B6D4))
}
