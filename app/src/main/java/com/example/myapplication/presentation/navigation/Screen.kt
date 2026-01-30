package com.example.myapplication.presentation.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object TodoMain : Screen("todo_main")
}
