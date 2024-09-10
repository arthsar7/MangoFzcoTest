package com.test.mangofzcotest.presentation.navigation.auth.phoneinput

import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.domain.usecases.auth.SendAuthCodeUseCase
import com.test.mangofzcotest.presentation.BaseViewModel
import com.test.mangofzcotest.presentation.navigation.screen.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthPhoneInputViewModel @Inject constructor(
    private val sendAuthCodeUseCase: SendAuthCodeUseCase
) : BaseViewModel<String>() {

    fun sendAuthCode(phone: String) {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading()
            sendAuthCodeUseCase(phone).onSuccess { isSuccess ->
                if (isSuccess) {
                    _screenState.value = ScreenState.Success(phone)
                    resetScreenState()
                }
                else {
                    _screenState.value = ScreenState.Error(FAILED_TO_SEND_AUTH_CODE)
                }
            }.handleFailure()
        }
    }
    companion object {
        private const val FAILED_TO_SEND_AUTH_CODE = "Failed to send auth code, please try again"
    }
}

