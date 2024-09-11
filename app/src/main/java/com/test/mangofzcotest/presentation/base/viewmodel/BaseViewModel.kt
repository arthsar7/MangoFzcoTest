package com.test.mangofzcotest.presentation.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.presentation.base.state.ScreenState
import com.text.mangofzcotest.core.utils.ifNullOrBlank
import com.text.mangofzcotest.core.utils.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel <T> : ViewModel() {
    private val _screenState = MutableStateFlow<ScreenState<T>>(ScreenState.Idle)
    val screenState = _screenState.asStateFlow()

    protected val currentState get() = _screenState.value

    private val Throwable?.errorMessage: String get() = this?.message.ifNullOrBlank { DEFAULT_ERROR_MESSAGE }


    protected fun clearState() {
        viewModelScope.launch {
            delay(RESET_DEFAULT_DELAY)
            _screenState.value = ScreenState.Idle
        }
    }

    protected fun emitSuccess(data: T) {
        _screenState.value = ScreenState.Success(data)
        logStateChange()
    }

    protected fun emitError(error: Throwable, data: T? = null) {
        _screenState.value = ScreenState.Error(error.errorMessage, data ?: currentState.data)
        logStateChange()
    }

    protected fun emitError(errorMessage: String, data: T? = null) {
        _screenState.value = ScreenState.Error(errorMessage, data ?: currentState.data)
        logStateChange()
    }

    protected fun emitLoading(data: T? = null) {
        _screenState.value = ScreenState.Loading(data ?: currentState.data)
        logStateChange()
    }

    private fun logStateChange() {
        log(currentState.toString())
    }

    protected fun <T> Result<T>.handleFailure() = onFailure(::emitError)

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Something went wrong"
        private const val RESET_DEFAULT_DELAY = 100L
    }
}