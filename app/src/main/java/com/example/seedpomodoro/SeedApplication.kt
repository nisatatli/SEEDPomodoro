package com.example.seedpomodoro

import android.app.Application
import com.example.seedpomodoro.data.local.SeedDatabase
import com.example.seedpomodoro.repository.SeedRepository
import com.example.seedpomodoro.data.local.UserSettingsRepository

class SeedApplication : Application() {

    val database by lazy { SeedDatabase.getDatabase(this) }
    val repository by lazy { SeedRepository(database.studySessionDao()) }
    val settingsRepository by lazy { UserSettingsRepository(this) }

}
