package com.test.mangofzcotest.presentation

import androidx.lifecycle.ViewModel
import com.test.mangofzcotest.presentation.navigation.auth.ScreenState
import com.text.mangofzcotest.core.utils.ifNullOrBlank
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {
    protected val mutableScreenState = MutableStateFlow<ScreenState>(ScreenState.Idle)
    val currentScreenState = mutableScreenState.asStateFlow()

    protected val Throwable?.errorMessage: String get() = this?.message.ifNullOrBlank { DEFAULT_ERROR_MESSAGE }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Что-то пошло не так"
    }
}