package com.example.myfirstwork.mvi

sealed interface JournalIntent {
    data class OnTextChange(val userInput: String) : JournalIntent
    object OnSave: JournalIntent
    object OnDelete: JournalIntent
    object OnEditingStart: JournalIntent //편집시작
    object OnUpdate: JournalIntent //수정 완료 버튼 클릭시
}