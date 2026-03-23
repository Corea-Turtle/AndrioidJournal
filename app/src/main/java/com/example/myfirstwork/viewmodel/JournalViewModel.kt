package com.example.myfirstwork.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstwork.mvi.JournalIntent
import com.example.myfirstwork.mvi.JournalSideEffect
import com.example.myfirstwork.mvi.JournalState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JournalViewModel: ViewModel() {

    //데이터 상태 - stateFlow - 콤포저블 UI - collect state
    private val _state = MutableStateFlow(JournalState())
    val state = _state.asStateFlow()

    //사이드 이펙트 -
    private val _sideEffect = Channel<JournalSideEffect> {}
    val sideEffect = _sideEffect.receiveAsFlow()

    init {

    }

    fun onIntent(intent: JournalIntent) {
        when (intent) {
            is JournalIntent.OnTextChange -> {
                _state.update {
                    it.copy(writingText = intent.userInput)
                }
            }

            JournalIntent.OnSave -> {
                save()
            }
        }
    }

    private fun save() {
        Log.d("TAG", "save: ")
        viewModelScope.launch {
            _sideEffect.send(JournalSideEffect.ShowSavedToast)
        }
    }
}