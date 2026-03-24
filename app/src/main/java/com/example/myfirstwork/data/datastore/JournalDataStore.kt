package com.example.myfirstwork.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.myfirstwork.data.model.Journal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// At the top level of your kotlin file:
val Context.journalDataStore: DataStore<Preferences> by preferencesDataStore(name = "journal_datastore")

class JournalDataStore(private val dataStore:DataStore<Preferences>) {
    companion object {
        val JOURNAL_LIST = stringPreferencesKey("journal_list")
    }
    //저장된 문자열을 가져오는 flow
    fun journalsFlow(): Flow<List<Journal>> = dataStore.data.map { preferences ->
        val storedJson = preferences[JOURNAL_LIST] ?: return@map emptyList()

        return@map Journal.decode(storedJson)
    }

    suspend fun save(updatedJournalList: List<Journal>) {
        dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[JOURNAL_LIST] = Journal.encode(updatedJournalList)
            }
        }
    }
}