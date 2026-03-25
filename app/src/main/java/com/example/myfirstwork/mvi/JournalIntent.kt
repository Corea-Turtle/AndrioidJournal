package com.example.myfirstwork.mvi

import androidx.compose.ui.text.input.TextFieldValue

sealed interface JournalIntent {
    data class OnTextChange(val userInput: TextFieldValue) : JournalIntent
    object OnSave: JournalIntent
    object OnDelete: JournalIntent
    object OnEditingStart: JournalIntent //편집시작
    object OnUpdate: JournalIntent //수정 완료 버튼 클릭시
}