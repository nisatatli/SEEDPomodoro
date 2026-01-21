package com.example.seedpomodoro.ui.theme.stats


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seedpomodoro.SeedApplication
import com.example.seedpomodoro.domain.stats.DailyStat
import com.example.seedpomodoro.domain.stats.StatsViewModel
import com.example.seedpomodoro.domain.stats.StatsViewModelFactory
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color


enum class StatsTab {
    DAILY, WEEKLY, YEARLY
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen() {

    val context = LocalContext.current
    val app = context.applicationContext as SeedApplication

    val viewModel: StatsViewModel = viewModel(
        factory = StatsViewModelFactory(app.repository)
    )

    val dailyStats by viewModel.dailyStats.collectAsState()
    val weeklyAverage by viewModel.weeklyAverage.collectAsState()
    val yearlyAverage by viewModel.yearlyAverage.collectAsState()

    var selectedTab by remember { mutableStateOf(StatsTab.DAILY) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Statistics") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            TabRow(selectedTabIndex = selectedTab.ordinal) {
                StatsTab.values().forEach { tab ->
                    Tab(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        text = { Text(tab.name.lowercase().replaceFirstChar { it.uppercase() }) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            when (selectedTab) {

                StatsTab.DAILY -> DailyStatsView(dailyStats)

                StatsTab.WEEKLY -> AverageCard(
                    title = "Weekly Average",
                    value = weeklyAverage
                )

                StatsTab.YEARLY -> AverageCard(
                    title = "Yearly Average",
                    value = yearlyAverage
                )
            }
        }
    }
}

@Composable
private fun DailyStatsView(stats: List<DailyStat>) {

    if (stats.isEmpty()) {
        Text("No data yet")
        return
    }

    val max = stats.maxOf { it.minutes }.coerceAtLeast(1)

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            stats.forEach { stat ->
                Box(
                    modifier = Modifier
                        .height((stat.minutes / max.toFloat() * 160).dp)
                        .width(24.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(6.dp)
                        )
                )
            }
        }


        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            stats.forEach { stat ->
                Card {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stat.dayLabel,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "${stat.minutes} min",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
@Composable
private fun AverageCard(title: String, value: Int) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("$value min", style = MaterialTheme.typography.headlineLarge)
        }
    }
}
