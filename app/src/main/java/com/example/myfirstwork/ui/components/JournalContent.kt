package com.example.myfirstwork.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstwork.mvi.JournalIntent
import com.example.myfirstwork.mvi.JournalState
import com.example.myfirstwork.mvi.JournalUiMode
import com.example.myfirstwork.ui.theme.Primary
import com.example.myfirstwork.ui.theme.TextGray
import java.time.LocalDate

@Composable
fun JournalContent(
    state: JournalState,
    onIntent: (JournalIntent) -> Unit,
    modifier: Modifier
) {
    when(state.uiMode) {
        JournalUiMode.LOADING -> {
            LoadingScreen()
        }
        else -> {
          MainContent(state, onIntent = onIntent, modifier)
        }
    }


}


@Composable
private fun MainContent(state: JournalState,
                        onIntent: (JournalIntent) -> Unit,
                        modifier: Modifier) {
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

            JournalUiMode.LOADING -> Unit
        }

        Spacer(modifier = Modifier.height(32.dp))

        HistorySection(state.journals)



    }
}

@Composable
private fun LoadingScreen(){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Primary.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ){
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = Primary
            )
        }

        Spacer(Modifier.height(12.dp))

        Text("한 줄 일기",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold)
        Text("오늘의 기록을 불러오는 중입니다...",
            fontSize = 13.sp, color = TextGray
        )
        Spacer(modifier = Modifier.height(20.dp))
        CircularProgressIndicator(
            color = Primary,
            strokeWidth = 2.dp,
            modifier = Modifier.size(28.dp))
    }
}