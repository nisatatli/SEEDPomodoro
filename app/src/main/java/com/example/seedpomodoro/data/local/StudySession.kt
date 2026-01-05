package com.example.seedpomodoro.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_sessions")
data class StudySession(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val durationMinutes: Int,
    val timestamp: Long
)
