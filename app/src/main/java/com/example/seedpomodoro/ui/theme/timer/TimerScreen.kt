package com.example.seedpomodoro.ui.theme.timer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seedpomodoro.SeedApplication
import com.example.seedpomodoro.domain.timer.TimerMode
import com.example.seedpomodoro.domain.timer.TimerViewModel
import com.example.seedpomodoro.domain.timer.TimerViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen() {

    val context = LocalContext.current
    val app = context.applicationContext as SeedApplication

    val timerViewModel: TimerViewModel = viewModel(
        factory = TimerViewModelFactory(
            repository = app.repository,
            settingsRepository = app.settingsRepository










        )
    )

    val timeLeft by timerViewModel.timeLeft.collectAsState()
    val isRunning by timerViewModel.isRunning.collectAsState()
    val mode by timerViewModel.mode.collectAsState()

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60

    // ⏱️ ŞİMDİLİK SABİT (ileride settings’e bağlanacak)
    val totalSeconds =
        if (mode == TimerMode.POMODORO) 25 * 60 else 5 * 60

    val progress = timeLeft / totalSeconds.toFloat()

    val modeColor =
        if (mode == TimerMode.POMODORO)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.secondary

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("SEED Pomodoro") })
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔁 MODE LABEL
            Text(
                text = if (mode == TimerMode.POMODORO) "Pomodoro" else "Break",
                style = MaterialTheme.typography.titleLarge,
                color = modeColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ⭕ PROGRESS + TIME
            Box(contentAlignment = Alignment.Center) {

                CircularProgressIndicator(
                    progress = { progress.coerceIn(0f, 1f) },
                    modifier = Modifier.size(200.dp),
                    strokeWidth = 10.dp,
                    color = modeColor
                )

                Text(
                    text = String.format("%02d:%02d", minutes, seconds),
                    style = MaterialTheme.typography.displayLarge
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ▶️ CONTROLS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                if (!isRunning) {
                    Button(
                        onClick = { timerViewModel.startTimer() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Start")
                    }
                } else {
                    Button(
                        onClick = { timerViewModel.pauseTimer() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Pause")
                    }
                }

                OutlinedButton(
                    onClick = { timerViewModel.resetTimer() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reset")
                }
            }
        }
    }
}
