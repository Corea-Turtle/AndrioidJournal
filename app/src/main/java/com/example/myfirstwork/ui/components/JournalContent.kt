package com.example.myfirstwork.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myfirstwork.mvi.JournalIntent
import com.example.myfirstwork.mvi.JournalState
import com.example.myfirstwork.mvi.JournalUiMode
import java.time.LocalDate

@Composable
fun JournalContent(
    state: JournalState,
    onIntent: (JournalIntent) -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {

        JournalHeader()

        Spacer(modifier = Modifier.height(24.dp))

        when (state.uiMode) {
            JournalUiMode.EMPTY -> {
                AddJournalCard(
                    content = state.writingText,
                    onTextChange = {
                        onIntent(JournalIntent.OnTextChange(it))
                    },
                    onSave = {
                        onIntent(JournalIntent.OnSave)
                    })
            }
            JournalUiMode.EDIT -> {
                EditJournalCard(
                    content = state.writingText,
                    onTextChange = {
                    onIntent(JournalIntent.OnTextChange(it))
                }, onUpdate = {
                    onIntent(JournalIntent.OnUpdate)
                })
            }
            JournalUiMode.READ -> {
                state.todayJournal?.let {
                    TodayJournalCard(journal = it, onStartEditing = {
                        onIntent(JournalIntent.OnEditingStart)
                    }, onDelete = {
                        onIntent(JournalIntent.OnDelete)
                    })
                }


            }
        }

        HistorySection()



    }
}