package com.example.seedpomodoro.data.local

data class StudySession(
    val sessionId: Int,
    val startTime: Long,
    val endTime: Long,
    val durationMinutes: Int,
    val isCompleted: Boolean,
    val date: String
)

