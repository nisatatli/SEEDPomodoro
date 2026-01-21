/*
package com.example.seedpomodoro.ui.theme.settings


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seedpomodoro.SeedApplication
import kotlin.math.roundToInt

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
            TopAppBar(title = { Text("Settings") })
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {


            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Pomodoro Time",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${settings.pomodoroMinutes} minutes",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Slider(
                        value = settings.pomodoroMinutes.toFloat(),
                        onValueChange = {
                            viewModel.setPomodoroMinutes(it.toInt())
                        },
                        valueRange = 1f..60f
                    )

                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Break Time",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${settings.breakMinutes} minutes",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Slider(
                        value = settings.breakMinutes.toFloat(),
                        onValueChange = {
                            viewModel.setBreakMinutes(it.roundToInt())
                        },
                        valueRange = 1f..30f
                    )

                }
            }
        }
    }
}
*/
package com.example.seedpomodoro.ui.theme.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
            TopAppBar(title = { Text("Settings") })
        }
    ) { innerPadding ->

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {

            val isTablet = maxWidth >= 600.dp

            Column(
                modifier = Modifier
                    .fillMaxWidth(if (isTablet) 0.6f else 1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {


                Card {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = "Pomodoro Time",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${settings.pomodoroMinutes} minutes",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Slider(
                            value = settings.pomodoroMinutes.toFloat(),
                            onValueChange = {
                                viewModel.setPomodoroMinutes(it.toInt())
                            },
                            valueRange = 1f..120f
                        )
                    }
                }


                Card {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = "Break Time",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${settings.breakMinutes} minutes",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Slider(
                            value = settings.breakMinutes.toFloat(),
                            onValueChange = {
                                viewModel.setBreakMinutes(it.toInt())
                            },
                            valueRange = 1f..60f
                        )
                    }
                }
            }
        }
    }
}
