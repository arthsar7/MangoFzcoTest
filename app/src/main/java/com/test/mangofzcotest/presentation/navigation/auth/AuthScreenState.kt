package com.test.mangofzcotest.presentation.navigation.auth


sealed interface ScreenState {
    data object Idle : ScreenState
    data object Loading : ScreenState
    data object Success : ScreenState
    data class Error(val errorMessage: String) : ScreenState
}

val ScreenState.isLoading: Boolean
    get() = this == ScreenState.Loading
