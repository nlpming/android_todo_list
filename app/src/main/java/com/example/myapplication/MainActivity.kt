package com.example.myapplication

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.local.datastore.UserPreferences
import com.example.myapplication.presentation.navigation.NavGraph
import com.example.myapplication.presentation.theme.TodoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply saved language setting
        runBlocking {
            val languageCode = userPreferences.language.first()
            updateLocale(languageCode)
        }

        enableEdgeToEdge()

        setContent {
            TodoAppTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    userPreferences = userPreferences,
                    onLanguageChange = {
                        recreate() // Recreate activity to apply new language
                    }
                )
            }
        }
    }

    private fun updateLocale(languageCode: String) {
        val locale = when (languageCode) {
            "en" -> Locale.ENGLISH
            "zh" -> Locale.SIMPLIFIED_CHINESE
            else -> Locale.SIMPLIFIED_CHINESE
        }
        Locale.setDefault(locale)

        val config = Configuration(resources.configuration)
        config.setLocale(locale)

        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
