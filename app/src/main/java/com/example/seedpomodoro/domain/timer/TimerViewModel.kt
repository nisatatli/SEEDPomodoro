package com.example.seedpomodoro.domain.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seedpomodoro.data.local.StudySession
import com.example.seedpomodoro.data.local.UserSettingsRepository
import com.example.seedpomodoro.repository.SeedRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TimerViewModel(
    private val repository: SeedRepository,
    private val settingsRepository: UserSettingsRepository
) : ViewModel() {

    private var timerJob: Job? = null

    private val _timeLeft = MutableStateFlow(0)
    val timeLeft: StateFlow<Int> = _timeLeft

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private val _mode = MutableStateFlow(TimerMode.POMODORO)
    val mode: StateFlow<TimerMode> = _mode

    private var pomodoroMinutes = 25
    private var breakMinutes = 5

    init {
        viewModelScope.launch {
            settingsRepository.settings.collect { settings ->
                pomodoroMinutes = settings.pomodoroMinutes
                breakMinutes = settings.breakMinutes
                resetTimer()
            }
        }
    }

    fun startTimer() {
        if (_isRunning.value) return

        _isRunning.value = true
        timerJob = viewModelScope.launch {
            while (_timeLeft.value > 0 && _isRunning.value) {
                delay(1000)
                _timeLeft.value -= 1
            }

            if (_timeLeft.value == 0) {
                onTimerFinished()
            }

            _isRunning.value = false
        }
    }

    fun pauseTimer() {
        _isRunning.value = false
    }

    fun resetTimer() {
        timerJob?.cancel()
        _timeLeft.value = when (_mode.value) {
            TimerMode.POMODORO -> pomodoroMinutes * 60
            TimerMode.BREAK -> breakMinutes * 60
        }
        _isRunning.value = false
    }

    private suspend fun onTimerFinished() {
        if (_mode.value == TimerMode.POMODORO) {
            saveSession()
            _mode.value = TimerMode.BREAK
        } else {
            _mode.value = TimerMode.POMODORO
        }
        resetTimer()
    }

    private suspend fun saveSession() {
        val session = StudySession(
            durationMinutes = pomodoroMinutes,
            timestamp = System.currentTimeMillis()
        )
        repository.saveSession(session)
    }
}
