/*
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
*/
package com.example.seedpomodoro.domain.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seedpomodoro.repository.SeedRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class StatsViewModel(
    private val repository: SeedRepository
) : ViewModel() {

    // Tüm session'lar
    val sessions = repository.getAllSessions()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    // 🔹 DAILY STATS
    val dailyStats: StateFlow<List<DailyStat>> =
        sessions.map { list ->
            val sdf = SimpleDateFormat("EEE", Locale.getDefault())

            list.groupBy {
                sdf.format(Date(it.timestamp))
            }.map { entry ->
                DailyStat(
                    dayLabel = entry.key,
                    minutes = entry.value.sumOf { it.durationMinutes }
                )
            }.takeLast(7)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    // 🔹 WEEKLY AVERAGE
    val weeklyAverage: StateFlow<Int> =
        sessions.map { list ->
            if (list.isEmpty()) 0
            else list.takeLast(7).sumOf { it.durationMinutes } / 7
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            0
        )

    // 🔹 YEARLY AVERAGE
    val yearlyAverage: StateFlow<Int> =
        sessions.map { list ->
            if (list.isEmpty()) 0
            else list.sumOf { it.durationMinutes } / 365
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            0
        )
}
