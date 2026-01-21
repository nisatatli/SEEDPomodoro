package com.example.seedpomodoro.ui.theme.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seedpomodoro.data.local.StudySession
import com.example.seedpomodoro.repository.SeedRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StatsViewModel(
    private val repository: SeedRepository
) : ViewModel() {

    // 🔹 Tüm session'lar
    val sessions: StateFlow<List<StudySession>> =
        repository.getAllSessions()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    // 🔹 Toplam session sayısı
    val totalSessions: StateFlow<Int> =
        sessions
            .map { it.size }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                0
            )

    // 🔹 Toplam dakika
    val totalMinutes: StateFlow<Int> =
        sessions
            .map { list -> list.sumOf { it.durationMinutes } }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                0
            )
}
