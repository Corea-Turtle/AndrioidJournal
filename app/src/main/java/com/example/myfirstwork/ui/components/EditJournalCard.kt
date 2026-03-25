package com.example.myfirstwork.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstwork.ui.theme.ButtonDisabled
import com.example.myfirstwork.ui.theme.ButtonEnabled
import com.example.myfirstwork.ui.theme.CardBg
import com.example.myfirstwork.ui.theme.Primary
import com.example.myfirstwork.ui.theme.TextGray
import com.example.myfirstwork.utils.toKoreanDate
import java.time.LocalDate

@Composable
fun EditJournalCard(
    content: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    onUpdate: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp)
        ){
            HeaderRow()
            Spacer(Modifier.height(16.dp))
            JournalTextField(value = content,
                onValueChanged = onTextChange)
            FooterRow(textLength = content.text.length,
                onSubmit = onUpdate,
                buttonText = "수정완료",
                enabled = content.text.isNotBlank())
        }

    }
}

@Composable
private fun HeaderRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                "EDIT SENTENCE",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Primary
            )
        }
        Text(LocalDate.now().toString().toKoreanDate(),
            fontSize = 12.sp, color = TextGray)
    }
}
@Composable
private fun FooterRow(
    textLength: Int,
    maxLength: Int = 50,
    enabled: Boolean,
    onSubmit: () -> Unit,
    buttonText: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("${textLength} / ${maxLength}",
            fontSize = 12.sp, color = TextGray)
        Button(
            onClick = onSubmit,
            enabled = enabled,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if(enabled) ButtonEnabled else ButtonDisabled)
        ){
            Text(buttonText)
        }
    }
}
@Composable
fun JournalTextField(
    value: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit
) {

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(value = value,
        onValueChange = {
            if(it.text.length <= 50) {
                onValueChanged(it)
            }
        }, textStyle = TextStyle(fontSize = 15.sp),
        modifier = Modifier.fillMaxWidth()
            .heightIn(min = 80.dp).focusRequester(focusRequester),
        decorationBox = { innerTextField ->
            Column {
                if (value.text.isEmpty()){
                    Text(
                        text = "오늘하루를 한 문장으로 기록해보세요.",
                        fontSize = 14.sp,
                        color = TextGray
                    )
                }
                innerTextField()
            }
        }
    )
}