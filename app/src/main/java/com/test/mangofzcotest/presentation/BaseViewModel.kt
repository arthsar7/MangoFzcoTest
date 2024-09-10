package com.test.mangofzcotest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.presentation.navigation.screen.ScreenState
import com.text.mangofzcotest.core.utils.ifNullOrBlank
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel <T> : ViewModel() {

    protected val _screenState = MutableStateFlow<ScreenState<T>>(ScreenState.Idle)
    val screenState = _screenState.asStateFlow()

    private val Throwable?.errorMessage: String get() = this?.message.ifNullOrBlank { DEFAULT_ERROR_MESSAGE }

    protected fun resetScreenState() {
        viewModelScope.launch {
            delay(RESET_DEFAULT_DELAY)
            _screenState.value = ScreenState.Idle
        }
    }

    protected fun <T> Result<T>.handleFailure() = onFailure {
            val currentScreenState = screenState.value
            val nextScreenState = ScreenState.Error(it.errorMessage, currentScreenState.data)
            _screenState.value = nextScreenState
        }
    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Something went wrong"
        private const val RESET_DEFAULT_DELAY = 100L
    }
}