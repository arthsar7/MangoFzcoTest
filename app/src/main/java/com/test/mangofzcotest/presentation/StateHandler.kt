package com.test.mangofzcotest.presentation

import androidx.compose.runtime.Composable
import com.test.mangofzcotest.presentation.navigation.screen.ScreenState

@Composable
fun <T> StateHandler(
    state: ScreenState<T>,
    loadingContent: @Composable () -> Unit,
    errorContent: @Composable (ScreenState.Error<T>) -> Unit,
    content: @Composable (ScreenState.Success<T>) -> Unit
) {
    when (state) {
        is ScreenState.Loading -> loadingContent()
        is ScreenState.Error -> errorContent(state)
        is ScreenState.Success -> content(state)
        ScreenState.Idle -> {}
    }
}