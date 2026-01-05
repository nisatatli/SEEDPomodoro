package com.example.seedpomodoro.repository

import com.example.seedpomodoro.data.local.StudySession
import com.example.seedpomodoro.data.local.StudySessionDao
import kotlinx.coroutines.flow.Flow

class SeedRepository(
    private val dao: StudySessionDao
) {

    suspend fun saveSession(session: StudySession) {
        dao.insertSession(session)
    }

    fun getAllSessions(): Flow<List<StudySession>> {
        return dao.getAllSessions()
    }
}
