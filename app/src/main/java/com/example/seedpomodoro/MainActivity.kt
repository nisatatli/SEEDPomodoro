package com.example.seedpomodoro

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding

import androidx.compose.ui.tooling.preview.Preview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.seedpomodoro.navigation.Screen
import com.example.seedpomodoro.ui.theme.SeedTheme
import com.example.seedpomodoro.ui.theme.stats.StatsScreen
import com.example.seedpomodoro.ui.theme.settings.SettingsScreen
import com.example.seedpomodoro.ui.theme.timer.TimerScreen
import com.example.seedpomodoro.data.local.SeedDatabase
import com.example.seedpomodoro.repository.SeedRepository
import com.example.seedpomodoro.ui.theme.welcome.WelcomeScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SeedTheme {
                SeedApp()
            }
        }
    }
}

@Composable
fun SeedApp() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Welcome.route) {
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Welcome.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Screen.Welcome.route) {
                WelcomeScreen(
                    onStartClick = {
                        navController.navigate(Screen.Timer.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Timer.route) {
                TimerScreen()
            }

            composable(Screen.Stats.route) {
                StatsScreen()
            }

            composable(Screen.Settings.route) {
                SettingsScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SeedAppPreview() {
    SeedTheme {
        SeedApp()
    }
}
