package com.example.viikko1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viikko1.routes.ROUTE_CALENDAR
import com.example.viikko1.routes.ROUTE_HOME
import com.example.viikko1.routes.ROUTE_SETTINGS
import com.example.viikko1.view.CalendarScreen
import com.example.viikko1.view.HomeScreen
import com.example.viikko1.view.SettingsScreen
import com.example.viikko1.ui.theme.Viikko1Theme
import com.example.viikko1.viewmodel.SettingsViewModel
import com.example.viikko1.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState()

            Viikko1Theme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                val taskViewModel: TaskViewModel = viewModel()

                NavHost(navController = navController, startDestination = ROUTE_HOME) {
                    composable(ROUTE_HOME) {
                        HomeScreen(
                            viewModel = taskViewModel,
                            onNavigateToCalendar = { navController.navigate(ROUTE_CALENDAR) },
                            onNavigateToSettings = { navController.navigate(ROUTE_SETTINGS) }
                        )
                    }
                    composable(ROUTE_CALENDAR) {
                        CalendarScreen(
                            viewModel = taskViewModel,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                    composable(ROUTE_SETTINGS) {
                        SettingsScreen(
                            viewModel = settingsViewModel,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
