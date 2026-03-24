package com.example.myfirstwork.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfirstwork.mvi.JournalIntent
import com.example.myfirstwork.mvi.JournalState
import java.time.LocalDate

@Composable
fun JournalContent(state: JournalState,
                   onIntent: (JournalIntent) -> Unit,
                   modifier: Modifier) {
    Column(modifier = modifier.padding(16.dp)) {

        Text(text = "오늘의 한줄 / ${LocalDate.now().toString()}")
        Text(text = "입력된 글자 / ${state.writingText}")
        TextField(value = state.writingText,
            onValueChange = { input ->
                onIntent(JournalIntent.OnTextChange(input))
            })
        ElevatedButton({
            onIntent(JournalIntent.OnSave) //1.
        })  {
            Text("저장")
        }

        LazyColumn {
            items(state.journals) { journal ->
                Text("✅ ${journal.date} - ${journal.content}")
            }
        }

    }
}