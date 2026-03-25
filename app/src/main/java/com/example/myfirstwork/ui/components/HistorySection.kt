package com.example.myfirstwork.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstwork.data.model.Journal
import com.example.myfirstwork.ui.theme.CardBg
import com.example.myfirstwork.ui.theme.TextGray

@Composable
fun HistorySection(journalList: List<Journal>) {

    if(journalList.isEmpty()) {
        EmptyHistory()
    } else {
        LazyColumn {
            stickyHeader {
                Text(
                    text = "지난 기록",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 12.dp))
            }
            items(journalList) { journal ->
                HistoryItemCard(journal)
            }
        }
    }
}

@Composable
private fun HistoryItemCard(journal: Journal) {
   Card(shape = RoundedCornerShape(20.dp),
       colors = CardDefaults.cardColors(containerColor = CardBg),
       modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
   ) {
       Column(modifier = Modifier.padding(16.dp)) {
            Text(text = journal.date, fontSize = 12.sp, color = TextGray)
            Text(text = journal.content)
       }
   }
}

@Composable
private fun EmptyHistory(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(48.dp)
    ){
        Text("아직 작성된 일기가 없습니다.",
            fontSize = 14.sp,
            color = TextGray
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text("오늘의 첫 문장을 기록해보세요.",
            fontSize = 14.sp,
            color = TextGray.copy(alpha = 0.7f)
        )
    }
}