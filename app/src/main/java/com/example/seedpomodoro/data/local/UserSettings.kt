package com.example.seedpomodoro.data.local

data class UserSettings(
    val id: Int = 1,
    val workDuration: Int,
    val breakDuration: Int,
    val longBreakDuration: Int,
    val themeMode: Boolean
)
