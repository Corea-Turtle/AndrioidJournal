package com.example.myfirstwork.viewmodel

import android.util.Log
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstwork.data.model.Journal
import com.example.myfirstwork.data.repository.JournalRepository
import com.example.myfirstwork.mvi.JournalIntent
import com.example.myfirstwork.mvi.JournalSideEffect
import com.example.myfirstwork.mvi.JournalState
import com.example.myfirstwork.mvi.JournalUiMode
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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

            delay(3000)

            repository.journals.collect { storedJournals ->
                Log.d("TAG", "저장된 journals: ${storedJournals}")

                val todayString = LocalDate.now().toString()
                val todayJournal = storedJournals.find { it.date == todayString }

                val mode = if(todayJournal == null) JournalUiMode.EMPTY else JournalUiMode.READ

                _state.update {
                    it.copy(
                        journals = storedJournals,
                        todayJournal = todayJournal,
                        uiMode = mode
                    )
                }
            }
        }
    }

    fun onIntent(intent: JournalIntent) {
        when (intent) {
            is JournalIntent.OnTextChange -> {
                _state.update {
                    it.copy(writingText = intent.userInput
                    )
                }
            }
            JournalIntent.OnSave -> {
                save() //3
            }

            JournalIntent.OnDelete -> {
                delete()
            }
            JournalIntent.OnEditingStart -> {
                val text = state.value.todayJournal?.content?: ""
                _state.update {
                    it.copy(uiMode = JournalUiMode.EDIT,
                        writingText = TextFieldValue(text = text, selection = TextRange(text.length)))
                }
            }

            JournalIntent.OnUpdate -> {
               update()
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
        _state.update {
            it.copy(writingText= TextFieldValue(), uiMode = JournalUiMode.EMPTY)
        }
        viewModelScope.launch{
            //삭제한다.
            repository.deleteJournal(LocalDate.now().toString())
            _sideEffect.send(JournalSideEffect.ShowUndoSnackbar)
        }
    }

    //수정로직
    private fun update() {
        Log.d("TAG", "update: ")

        _state.update {
            it.copy(uiMode = JournalUiMode.READ)
        }
        viewModelScope.launch {
            repository.updateJournal(
                date = LocalDate.now().toString(),
                content = state.value.writingText.text
            )
            _sideEffect.send(JournalSideEffect.ShowToast("수정이 완료되었습니다."))
        }
    }

    //저장로직
    private fun save() {
        Log.d("TAG", "save: ")
        viewModelScope.launch {
            _state.update {
                it.copy(uiMode = JournalUiMode.READ)
            }
            //저장 로직은 레포지토리에 맡긴다.
            repository.saveJournal(date = LocalDate.now().toString(),
                content = state.value.writingText.text)

            _sideEffect.send(JournalSideEffect.ShowSavedToast)
        }
    }
}