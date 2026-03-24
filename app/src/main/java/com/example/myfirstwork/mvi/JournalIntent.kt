package com.example.myfirstwork.mvi

sealed interface JournalIntent {
    data class OnTextChange(val userInput: String) : JournalIntent
    object OnSave: JournalIntent
    object OnDelete: JournalIntent
    object OnUpdate: JournalIntent
}