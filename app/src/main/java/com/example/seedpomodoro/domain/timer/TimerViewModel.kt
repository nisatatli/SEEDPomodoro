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

    // Aktif pomodoro süresi (dk)
    private var currentPomodoroMinutes: Int = 25

    init {
        // 🔥 Settings'i dinle
        viewModelScope.launch {
            settingsRepository.settings.collect { settings ->
                currentPomodoroMinutes = settings.pomodoroMinutes

                // ⛔ Timer çalışıyorsa otomatik resetleme
                if (!_isRunning.value) {
                    resetTimer()
                }
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
                saveCompletedSession()
            }

            _isRunning.value = false
        }
    }

    fun pauseTimer() {
        _isRunning.value = false
    }

    fun resetTimer() {
        timerJob?.cancel()
        _timeLeft.value = currentPomodoroMinutes * 60
        _isRunning.value = false
    }

    // 🔥 Pomodoro tamamlanınca DB’ye kaydet
    private suspend fun saveCompletedSession() {
        val session = StudySession(
            durationMinutes = currentPomodoroMinutes,
            timestamp = System.currentTimeMillis()
        )
        repository.saveSession(session)
    }
}
