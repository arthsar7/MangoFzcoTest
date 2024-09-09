package com.test.mangofzcotest.presentation.navigation.auth

import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.domain.usecases.auth.SendAuthCodeUseCase
import com.test.mangofzcotest.presentation.BaseViewModel
import com.text.mangofzcotest.core.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sendAuthCodeUseCase: SendAuthCodeUseCase
) : BaseViewModel() {

    fun sendAuthCode(phone: String) {
        viewModelScope.launch {
            mutableScreenState.value = ScreenState.Loading
            val result = sendAuthCodeUseCase(phone)
            log("sendAuthCode: $result")

            if (result.isSuccess && result.getOrNull() == true) {
                mutableScreenState.value = ScreenState.Success
            }
            else {
                val exception = result.exceptionOrNull()
                mutableScreenState.value = ScreenState.Error(exception.errorMessage)
            }
        }
    }
}

