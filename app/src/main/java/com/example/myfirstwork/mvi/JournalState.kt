package com.example.myfirstwork.mvi

import com.example.myfirstwork.data.model.Journal

// 현재 데이터 상태
data class JournalState(
    val writingText: String = "",
    val todayJournal: Journal? = null,
    val isEditing: Boolean = false,
    val journals : List<Journal> = emptyList()
)