package com.example.myfirstwork.ui.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.myfirstwork.mvi.JournalSideEffect
import com.example.myfirstwork.ui.components.JournalContent
import com.example.myfirstwork.viewmodel.JournalViewModel

// 뷰모델로부터 state 구독
// 뷰모델로부터 sideEffect 구독
@Composable
fun JournalScreen(viewModel: JournalViewModel, modifier: Modifier) {

    val state by viewModel.state.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit)  {
        viewModel.sideEffect.collect { effect ->
            val toastMessage = when ( effect ) {
                JournalSideEffect.ShowSavedToast -> "저장되었습니다."
                else -> ""
            }
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT)
        }
    }

    JournalContent(state = state, onIntent = viewModel::onIntent, modifier)
}