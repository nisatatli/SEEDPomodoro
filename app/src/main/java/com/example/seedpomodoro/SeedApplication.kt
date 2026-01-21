package com.example.seedpomodoro

import android.app.Application
import androidx.room.Room
import com.example.seedpomodoro.data.local.SeedDatabase
import com.example.seedpomodoro.data.local.UserSettingsRepository
import com.example.seedpomodoro.repository.SeedRepository

class SeedApplication : Application() {

    lateinit var database: SeedDatabase
        private set

    lateinit var repository: SeedRepository
        private set

    lateinit var settingsRepository: UserSettingsRepository
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            SeedDatabase::class.java,
            "seed_database"
        ).build()

        repository = SeedRepository(
            dao = database.studySessionDao()
        )

        settingsRepository = UserSettingsRepository(applicationContext)
    }
}
