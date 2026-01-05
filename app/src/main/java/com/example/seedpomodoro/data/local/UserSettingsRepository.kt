package com.example.seedpomodoro.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_settings")

class UserSettingsRepository(private val context: Context) {

    companion object {
        val POMODORO_MINUTES = intPreferencesKey("pomodoro_minutes")
        val BREAK_MINUTES = intPreferencesKey("break_minutes")
    }

    val settings: Flow<UserSettings> = context.dataStore.data.map { prefs ->
        UserSettings(
            pomodoroMinutes = prefs[POMODORO_MINUTES] ?: 25,
            breakMinutes = prefs[BREAK_MINUTES] ?: 5
        )
    }

    suspend fun setPomodoroMinutes(minutes: Int) {
        context.dataStore.edit { it[POMODORO_MINUTES] = minutes }
    }

    suspend fun setBreakMinutes(minutes: Int) {
        context.dataStore.edit { it[BREAK_MINUTES] = minutes }
    }
}
