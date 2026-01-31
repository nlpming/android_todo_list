package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.data.local.datastore.UserPreferences
import com.example.myapplication.presentation.login.LoginScreen
import com.example.myapplication.presentation.register.RegisterScreen
import com.example.myapplication.presentation.todo.TodoScreen
import kotlinx.coroutines.flow.first

@Composable
fun NavGraph(
    navController: NavHostController,
    userPreferences: UserPreferences,
    onLanguageChange: () -> Unit
) {
    var startDestination by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val userId = userPreferences.userId.first()
        startDestination = if (userId != null) {
            Screen.TodoMain.route
        } else {
            Screen.Login.route
        }
    }

    if (startDestination != null) {
        NavHost(
            navController = navController,
            startDestination = startDestination!!
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.TodoMain.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }

            composable(Screen.Register.route) {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.TodoMain.route) {
                TodoScreen(
                    onLogout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.TodoMain.route) { inclusive = true }
                        }
                    },
                    onLanguageChange = onLanguageChange
                )
            }
        }
    }
}
