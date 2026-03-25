package com.example.myfirstwork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myfirstwork.data.datastore.JournalDataStore
import com.example.myfirstwork.data.datastore.journalDataStore
import com.example.myfirstwork.data.repository.JournalRepository
import com.example.myfirstwork.ui.screens.JournalScreen
import com.example.myfirstwork.ui.theme.MyFirstWorkTheme
import com.example.myfirstwork.viewmodel.JournalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dataStore = JournalDataStore(applicationContext.journalDataStore)

        val repository = JournalRepository(dataStore)

        val viewModel = JournalViewModel(repository)

        setContent {
            MyFirstWorkTheme {
                JournalScreen(viewModel)

            }
        }
    }
}