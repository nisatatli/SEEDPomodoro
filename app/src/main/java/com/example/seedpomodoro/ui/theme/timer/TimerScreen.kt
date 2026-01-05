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

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Pomodoro") })
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

            // 🟩 TIMER CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = String.format("%02d:%02d", minutes, seconds),
                        style = MaterialTheme.typography.displayLarge
                    )

                    Spacer(modifier = Modifier.height(24.dp))

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
    }
}
