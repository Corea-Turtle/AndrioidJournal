package com.example.myfirstwork.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstwork.data.model.Journal
import com.example.myfirstwork.data.repository.JournalRepository
import com.example.myfirstwork.mvi.JournalIntent
import com.example.myfirstwork.mvi.JournalSideEffect
import com.example.myfirstwork.mvi.JournalState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class JournalViewModel(
    private val repository: JournalRepository
): ViewModel() {

    //데이터 상태 - stateFlow - 콤포저블 UI - collect state
    private val _state = MutableStateFlow(JournalState())
    val state = _state.asStateFlow()

    //사이드 이펙트 -
    private val _sideEffect = Channel<JournalSideEffect> {}
    val sideEffect = _sideEffect.receiveAsFlow()

    private var deletedJournal : Journal? = null

    init {
        viewModelScope.launch {
            repository.journals.collect { storedJournals ->
                Log.d("TAG", "저장된 journals: ${storedJournals}")

                val todayString = LocalDate.now().toString()
                val todayJournal = storedJournals.find { it.date == todayString }

                _state.update {
                    it.copy(journals = storedJournals, todayJournal = todayJournal)
                }
            }
        }
    }

    fun onIntent(intent: JournalIntent) {
        when (intent) {
            is JournalIntent.OnTextChange -> {
                _state.update {
                    it.copy(writingText = intent.userInput)
                }
            }
            JournalIntent.OnSave -> {
                save() //3
            }

            JournalIntent.OnDelete -> {
                delete()
            }

            JournalIntent.OnUpdate -> {
                val text = state.value.todayJournal?.content?: ""
                _state.update {
                    it.copy(isEditing = true, writingText = text)
                }
            }
            else -> {}
        }
    }

    //삭제복구 로직
    fun undoDelete() {
        Log.d("TAG", "undoDelete: ")

        val journal = this.deletedJournal ?: return

        viewModelScope.launch {
            repository.saveJournal(date = journal.date, content = journal.content)
            _sideEffect.send(JournalSideEffect.ShowToast("복구되었습니다."))
        }
    }

    //삭제로직
    private fun delete() {
        Log.d("TAG", "delete: ")

        this.deletedJournal = state.value.todayJournal

        viewModelScope.launch{
            //삭제한다.
            repository.deleteJournal(LocalDate.now().toString())
            _sideEffect.send(JournalSideEffect.ShowUndoSnackbar)
        }
    }

    //저장로직
    private fun save() {
        Log.d("TAG", "save: ")
        viewModelScope.launch {
            if(state.value.isEditing) {
                _state.update {
                    it.copy(isEditing = false)
                }
            }
            //저장 로직은 레포지토리에 맡긴다.
            repository.saveJournal(date = LocalDate.now().toString(),
                content = state.value.writingText)

            //일기 데이터 추가하기
//            _state.update{
//                it.copy(journals = updatedJournals)
//            }

            _sideEffect.send(JournalSideEffect.ShowSavedToast)
        }
    }
}