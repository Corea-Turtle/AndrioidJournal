package com.example.myfirstwork.mvi

//SideEffect는 1번 실행하는 액션 - 상태 안건드림(상태는 인텐트에서)
sealed interface JournalSideEffect {
    object ShowSavedToast : JournalSideEffect
    data class ShowToast(val message: String) : JournalSideEffect
}