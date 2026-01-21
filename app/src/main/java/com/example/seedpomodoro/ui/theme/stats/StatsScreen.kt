package com.example.seedpomodoro.ui.theme.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seedpomodoro.SeedApplication
import com.example.seedpomodoro.ui.theme.stats.StatsViewModel
import com.example.seedpomodoro.ui.theme.stats.StatsViewModelFactory

import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen() {

    val context = LocalContext.current
    val app = context.applicationContext as SeedApplication

    val viewModel: StatsViewModel = viewModel(
        factory = StatsViewModelFactory(app.repository)
    )

    val totalSessions by viewModel.totalSessions.collectAsState()
    val totalMinutes by viewModel.totalMinutes.collectAsState()
    val sessions by viewModel.sessions.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Statistics") })
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Total Session", style = MaterialTheme.typography.labelLarge)
                        Text("$totalSessions", style = MaterialTheme.typography.headlineMedium)
                    }
                }
            }

            item {
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Total Minutes (m)", style = MaterialTheme.typography.labelLarge)
                        Text("$totalMinutes", style = MaterialTheme.typography.headlineMedium)
                    }
                }
            }

            items(sessions) { session ->
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Süre: ${session.durationMinutes} m")
                        Text(
                            "Date: ${
                                SimpleDateFormat(
                                    "dd MMM yyyy - HH:mm",
                                    Locale.getDefault()
                                ).format(Date(session.timestamp))
                            }"
                        )
                    }
                }
            }
        }
    }
}
