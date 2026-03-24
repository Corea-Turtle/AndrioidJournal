package com.example.myfirstwork.data.repository

import com.example.myfirstwork.data.datastore.JournalDataStore
import com.example.myfirstwork.data.model.Journal
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class JournalRepository(
    private val dataStore: JournalDataStore
) {

    val journals = dataStore.journalsFlow()

    suspend fun saveJournal(date: String, content: String) {
        val existingJournals = dataStore.journalsFlow().first()

        // 이미 작성된 일기가 있다면 방지

        val updatedJournals = existingJournals + Journal(
            content = content,
            date = date
        )

        dataStore.save(updatedJournals)
    }

    suspend fun updateJournal(date: String, content: String) {
        val updated = dataStore.journalsFlow().first().map  {
            if (it.date == date) {
                return@map it.copy(content = content)
            }else {
                return@map it
            }
        }
        dataStore.save(updated)
    }

    suspend fun deleteJournal(date: String) {
        //
        val updated = dataStore.journalsFlow().first().filterNot { it.date == date }
        dataStore.save(updated)
    }
}