package com.example.seedpomodoro.domain.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seedpomodoro.data.local.StudySession
import com.example.seedpomodoro.repository.SeedRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StatsViewModel(
    private val repository: SeedRepository
) : ViewModel() {

    private val _sessions = MutableStateFlow<List<StudySession>>(emptyList())
    val sessions: StateFlow<List<StudySession>> = _sessions

    val totalSessions: StateFlow<Int> =
        sessions.map { it.size }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            0
        )

    val totalMinutes: StateFlow<Int> =
        sessions.map { list ->
            list.sumOf { it.durationMinutes }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            0
        )

    init {
        viewModelScope.launch {
            repository.getAllSessions().collect {
                _sessions.value = it
            }
        }
    }
}
