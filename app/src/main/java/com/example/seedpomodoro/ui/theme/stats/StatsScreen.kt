package com.example.seedpomodoro.ui.theme.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seedpomodoro.SeedApplication
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen() {

    // 🔥 Application & Repository
    val context = LocalContext.current
    val app = context.applicationContext as SeedApplication

    // 🔥 ViewModel (Factory ile)
    val viewModel: StatsViewModel = viewModel(
        factory = StatsViewModelFactory(app.repository)
    )

    val uiState by viewModel.uiState.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("İstatistikler") })
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            // 🔥 ÖZET KART
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Toplam Session", style = MaterialTheme.typography.labelLarge)
                        Text(
                            text = uiState.totalSessions.toString(),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    Column {
                        Text("Toplam Süre (dk)", style = MaterialTheme.typography.labelLarge)
                        Text(
                            text = uiState.totalMinutes.toString(),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 📋 LİSTE
            if (uiState.sessions.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text("Henüz kayıtlı bir çalışma yok.")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.sessions) { session ->
                        SessionItem(session)
                    }
                }
            }
        }
    }

}
@Composable
fun SessionItem(session: com.example.seedpomodoro.data.local.StudySession) {

    val dateFormat = remember {
        SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault())
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Süre: ${session.durationMinutes} dk",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Tarih: ${dateFormat.format(Date(session.timestamp))}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
