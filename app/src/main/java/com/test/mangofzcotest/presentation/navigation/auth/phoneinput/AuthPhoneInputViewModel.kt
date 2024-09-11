package com.test.mangofzcotest.presentation.navigation.auth.phoneinput

import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.domain.usecases.auth.SendAuthCodeUseCase
import com.test.mangofzcotest.presentation.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthPhoneInputViewModel @Inject constructor(
    private val sendAuthCodeUseCase: SendAuthCodeUseCase
) : BaseViewModel<String>() {

    fun sendAuthCode(phone: String) {
        viewModelScope.launch {
            emitLoading()
            sendAuthCodeUseCase(phone).onSuccess { isSuccess ->
                if (isSuccess) {
                    emitSuccess(phone)
                    clearState()
                }
                else {
                    emitError(FAILED_TO_SEND_AUTH_CODE)
                }
            }.handleFailure()
        }
    }
    companion object {
        private const val FAILED_TO_SEND_AUTH_CODE = "Failed to send auth code, please try again"
    }
}

