package com.example.myfirstwork.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstwork.ui.theme.Primary
import com.example.myfirstwork.ui.theme.TextGray

@Composable
fun JournalHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
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
        Text("매일 하나의 문장으로 하루를 기억하세요.",
            fontSize = 13.sp, color = TextGray
        )
    }
}