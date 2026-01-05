package com.example.seedpomodoro.ui.theme.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seedpomodoro.SeedApplication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {

    val context = LocalContext.current
    val app = context.applicationContext as SeedApplication

    val viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(app.settingsRepository)
    )

    val settings by viewModel.settings.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Ayarlar") })
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // ⏱️ Pomodoro Süresi Kartı
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "⏱ Pomodoro Süresi",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${settings.pomodoroMinutes} dakika",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Slider(
                        value = settings.pomodoroMinutes.toFloat(),
                        onValueChange = {
                            viewModel.setPomodoroMinutes(it.toInt())
                        },
                        valueRange = 10f..60f,
                        steps = 4
                    )
                }
            }

            // ☕ Mola Süresi Kartı
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "☕ Mola Süresi",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${settings.breakMinutes} dakika",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Slider(
                        value = settings.breakMinutes.toFloat(),
                        onValueChange = {
                            viewModel.setBreakMinutes(it.toInt())
                        },
                        valueRange = 3f..15f,
                        steps = 3
                    )
                }
            }
        }
    }
}
