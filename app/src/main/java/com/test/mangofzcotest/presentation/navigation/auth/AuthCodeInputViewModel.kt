package com.test.mangofzcotest.presentation.navigation.auth

import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.domain.usecases.auth.CheckAuthCodeUseCase
import com.test.mangofzcotest.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthCodeInputViewModel @Inject constructor(
    private val checkAuthCodeUseCase: CheckAuthCodeUseCase,
) : BaseViewModel() {

    fun checkAuthCode(
        phone: String,
        code: String,
        onRegister: (phone: String) -> Unit,
        onSuccess: (phone: String) -> Unit
    ) {
        viewModelScope.launch {
            mutableScreenState.value = ScreenState.Loading
            val result = checkAuthCodeUseCase(phone, code)
            if (result.isSuccess && result.getOrNull() != null) {
                val isUserExists = result.getOrThrow().isUserExists
                if (!isUserExists) {
                    onRegister(phone)
                }
                else {
                    onSuccess(phone)
                }
            }
            else {
                val exception = result.exceptionOrNull()
                mutableScreenState.value = ScreenState.Error(exception.errorMessage)
            }
        }
    }
}