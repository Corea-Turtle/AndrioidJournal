package com.example.myfirstwork.ui.components

import android.icu.text.SymbolTable


import androidx.compose.material.icons.Icons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstwork.data.model.Journal
import com.example.myfirstwork.ui.theme.ButtonGray
import com.example.myfirstwork.ui.theme.CardBg
import com.example.myfirstwork.ui.theme.Primary
import com.example.myfirstwork.ui.theme.TextGray
import com.example.myfirstwork.utils.toKoreanDate

@Composable
fun TodayJournalCard(
    journal: Journal,
    onStartEditing: () -> Unit,
    onDelete: ()-> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(text = journal.date.toKoreanDate(),
                fontSize = 13.sp,
                color = TextGray,
                modifier = Modifier.padding(bottom = 4.dp)
                )
            Text("오늘의 기록 완료", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            Text("\"${journal.content}\"",
                fontStyle = FontStyle.Italic,
                fontSize = 16.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionButton(text = "수정",
                    icon = Icons.Default.Edit,
                    textColor = ButtonGray,
                    onClick = onStartEditing
                )
                ActionButton(text = "삭제",
                    icon = Icons.Default.Delete,
                    textColor = ButtonGray,
                    onClick = onDelete
                )
            }
        }

    }
}