package com.example.myfirstwork.mvi

import androidx.compose.ui.text.input.TextFieldValue
import com.example.myfirstwork.data.model.Journal


//1. 오늘 글이 비어있는 상태
//2. 수정중일 때
//3. 읽기모드
enum class JournalUiMode{
    EMPTY,
    EDIT,
    READ
}


// 현재 데이터 상태
data class JournalState(
    val writingText: TextFieldValue = TextFieldValue(),
    val todayJournal: Journal? = null,
    val uiMode: JournalUiMode = JournalUiMode.READ,
    val journals : List<Journal> = emptyList()
)