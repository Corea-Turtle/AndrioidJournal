package com.example.myfirstwork.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.myfirstwork.mvi.JournalSideEffect
import com.example.myfirstwork.ui.components.JournalContent
import com.example.myfirstwork.viewmodel.JournalViewModel

// 뷰모델로부터 state 구독
// 뷰모델로부터 sideEffect 구독
@Composable
fun JournalScreen(viewModel: JournalViewModel) {

    val state by viewModel.state.collectAsState()

    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit)  {
        viewModel.sideEffect.collect { effect ->
            val toastMessage = when ( effect ) {
                JournalSideEffect.ShowSavedToast -> "저장되었습니다."
                is JournalSideEffect.ShowToast -> effect.message
                else -> ""
            }
            if(effect != JournalSideEffect.ShowUndoSnackbar) {
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            }
            if(effect == JournalSideEffect.ShowUndoSnackbar) {
                val result = snackbarHostState
                    .showSnackbar(
                        message = "일기를 삭제했습니다.",
                        actionLabel = "삭제복구",
                        //Defaults to SnakbarDuration.Short
                        duration = SnackbarDuration.Short
                    )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        // Handle snackbar action performed
                        Log.d("TAG","JournalScreen: 삭제 복구 클릭")
                        viewModel.undoDelete()
                    }
                    SnackbarResult.Dismissed -> {
                        // Handle snackbar dismissed
                        Log.d("TAG","JournalScreen: 창 닫기")
                    }
                }
            }
        }

    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize()) { innerPadding ->
        JournalContent(state = state, onIntent = viewModel::onIntent, Modifier.padding(innerPadding))
    }

}