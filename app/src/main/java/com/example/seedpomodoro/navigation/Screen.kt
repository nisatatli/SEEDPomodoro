package com.example.seedpomodoro.navigation


sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Timer : Screen("timer")
    object Stats : Screen("stats")
    object Settings : Screen("settings")
}

