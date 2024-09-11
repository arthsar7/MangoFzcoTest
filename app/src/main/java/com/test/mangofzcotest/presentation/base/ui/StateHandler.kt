package com.test.mangofzcotest.presentation.base.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import com.test.mangofzcotest.presentation.base.state.ScreenState

@Composable
fun <T> StateHandler(
    state: ScreenState<T>,
    showErrorData: Boolean = true,
    showLoadingData: Boolean = true,
    loadingContent: @Composable ColumnScope.() -> Unit,
    errorContent: @Composable ColumnScope.(error: String) -> Unit,
    content: @Composable (T) -> Unit
) {
    when (state) {
        is ScreenState.Loading -> {
            Column {
                if (showLoadingData) {
                    state.data?.let { content(it) }
                }
                loadingContent()
            }
        }
        is ScreenState.Error -> {
            Column {
                if (showErrorData) {
                    state.data?.let { content(it) }
                }
                errorContent(state.errorMessage)
            }
        }
        is ScreenState.Success -> {
            content(state.data)
        }
        ScreenState.Idle -> {}
    }
}