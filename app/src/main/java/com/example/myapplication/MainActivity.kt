package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.local.datastore.UserPreferences
import com.example.myapplication.presentation.navigation.NavGraph
import com.example.myapplication.presentation.theme.TodoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TodoAppTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    userPreferences = userPreferences
                )
            }
        }
    }
}
