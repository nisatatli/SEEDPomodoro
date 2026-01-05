package com.example.seedpomodoro.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [StudySession::class],
    version = 1,
    exportSchema = false
)
abstract class SeedDatabase : RoomDatabase() {

    abstract fun studySessionDao(): StudySessionDao

    companion object {
        @Volatile
        private var INSTANCE: SeedDatabase? = null

        fun getDatabase(context: Context): SeedDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SeedDatabase::class.java,
                    "seed_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
