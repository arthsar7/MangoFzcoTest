package com.test.mangofzcotest.presentation.base.state

import androidx.compose.runtime.Immutable

@Immutable
sealed class ScreenState<out T>(open val data: T? = null) {
    data object Idle : ScreenState<Nothing>()
    data class Loading<T>(override val data: T? = null) : ScreenState<T>(data)
    data class Success<T> (override val data: T) : ScreenState<T>(data)
    data class Error<T>(val errorMessage: String, override val data: T? = null) : ScreenState<T>(data)
}

val ScreenState<*>.isLoading: Boolean
    get() = this is ScreenState.Loading

val ScreenState<*>.isSuccess: Boolean
    get() = this is ScreenState.Success

val ScreenState<*>.isError: Boolean
    get() = this is ScreenState.Error
