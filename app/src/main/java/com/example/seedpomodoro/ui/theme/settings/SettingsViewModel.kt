package com.example.seedpomodoro.ui.theme.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seedpomodoro.data.local.UserSettings
import com.example.seedpomodoro.data.local.UserSettingsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: UserSettingsRepository
) : ViewModel() {

    val settings: StateFlow<UserSettings> =
        repository.settings.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserSettings()
        )

    fun setPomodoroMinutes(minutes: Int) {
        viewModelScope.launch {
            repository.setPomodoroMinutes(minutes)
        }
    }

    fun setBreakMinutes(minutes: Int) {
        viewModelScope.launch {
            repository.setBreakMinutes(minutes)
        }
    }
}
