package com.example.seedpomodoro.ui.theme.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seedpomodoro.data.local.StudySession
import com.example.seedpomodoro.repository.SeedRepository
import kotlinx.coroutines.flow.*

data class StatsUiState(
    val sessions: List<StudySession> = emptyList(),
    val totalSessions: Int = 0,
    val totalMinutes: Int = 0
)

class StatsViewModel(
    repository: SeedRepository
) : ViewModel() {

    val uiState: StateFlow<StatsUiState> =
        repository.getAllSessions()
            .map { sessions ->
                StatsUiState(
                    sessions = sessions,
                    totalSessions = sessions.size,
                    totalMinutes = sessions.sumOf { it.durationMinutes }
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = StatsUiState()
            )
}
