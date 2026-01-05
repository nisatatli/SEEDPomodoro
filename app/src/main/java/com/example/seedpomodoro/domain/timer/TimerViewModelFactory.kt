package com.example.seedpomodoro.domain.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.seedpomodoro.data.local.UserSettingsRepository
import com.example.seedpomodoro.repository.SeedRepository

class TimerViewModelFactory(
    private val repository: SeedRepository,
    private val settingsRepository: UserSettingsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimerViewModel(
                repository = repository,
                settingsRepository = settingsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
