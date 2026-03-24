package com.example.myfirstwork.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import java.time.LocalDate

@Composable
fun JournalContent(state: JournalState,
                   onIntent: (JournalIntent) -> Unit,
                   modifier: Modifier) {
    Column(modifier = modifier.padding(16.dp)) {

        Text(text = "오늘의 한줄 / ${LocalDate.now().toString()}")
        Text(text = "입력된 글자 / ${state.writingText}")


        if(state.todayJournal != null) {
            Column(modifier = Modifier.background(Color.Yellow)){
                Text("오늘 작성한 일기 / ${state.todayJournal.date}")
                Text("오늘 작성한 일기 / ${state.todayJournal.content}")
                Row() {
                    ElevatedButton({

                    }) {
                        Text("수정")
                    }
                    ElevatedButton({
                        onIntent(JournalIntent.OnDelete)
                    }){
                        Text("삭제")
                    }
                }
            }
        } else {
            TextField(value = state.writingText,
                onValueChange = { input ->
                    onIntent(JournalIntent.OnTextChange(input))
                })
            ElevatedButton({
                onIntent(JournalIntent.OnSave) //1.
            })  {
                Text("저장")
            }
        }


        LazyColumn {
            items(state.journals) { journal ->
                Text("✅ ${journal.date} - ${journal.content}")
            }
        }

    }
}