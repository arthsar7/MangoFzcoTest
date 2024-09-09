package com.test.mangofzcotest.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.domain.usecases.auth.CheckAuthCodeUseCase
import com.test.mangofzcotest.domain.usecases.auth.RegisterUseCase
import com.test.mangofzcotest.domain.usecases.auth.SendAuthCodeUseCase
import com.text.mangofzcotest.core.utils.ifNullOrBlank
import com.text.mangofzcotest.core.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val checkAuthCodeUseCase: CheckAuthCodeUseCase,
    private val registerUseCase: RegisterUseCase,
    private val sendAuthCodeUseCase: SendAuthCodeUseCase
) : ViewModel() {

    private val _authScreenState = MutableStateFlow<AuthScreenState>(AuthScreenState.PhoneInput())
    val authScreenState = _authScreenState.asStateFlow()

    fun sendAuthCode(phone: String) {
        viewModelScope.launch {
            _authScreenState.value = AuthScreenState.PhoneInput(isLoading = true)
            val result = sendAuthCodeUseCase(phone)
            log("sendAuthCode: $result")
            _authScreenState.value = AuthScreenState.PhoneInput(isLoading = false)

            if (result.isSuccess && result.getOrNull() == true) {
                _authScreenState.value = AuthScreenState.SmsCodeInput(phoneNumber = phone)
            }
            else {
                val exception = result.exceptionOrNull()
                _authScreenState.value = AuthScreenState.PhoneInput(
                    isLoading = false,
                    isError = true,
                    errorMessage = exception?.message.ifNullOrBlank { DEFAULT_ERROR_MESSAGE }
                )
            }
        }
    }

    fun checkAuthCode(phone: String, code: String) {
        viewModelScope.launch {
            _authScreenState.value =
                AuthScreenState.SmsCodeInput(phoneNumber = phone, isLoading = true)
            val result = checkAuthCodeUseCase(phone, code)
            _authScreenState.value =
                AuthScreenState.SmsCodeInput(phoneNumber = phone, isLoading = false)
            log("checkAuthCode: $result")
            if (result.isSuccess && result.getOrNull() != null) {
                val isUserExists = result.getOrThrow().isUserExists
                if (!isUserExists) {
                    _authScreenState.value = AuthScreenState.Register(phoneNumber = phone)
                }
                else {
                    _authScreenState.value = AuthScreenState.Success
                }
            }
            else {
                val exception = result.exceptionOrNull()
                _authScreenState.value = AuthScreenState.SmsCodeInput(
                    phoneNumber = phone,
                    isLoading = false,
                    isError = true,
                    errorMessage = exception?.message.ifNullOrBlank { DEFAULT_ERROR_MESSAGE }
                )
            }
        }
    }

    fun register(phone: String, name: String, username: String) {
        viewModelScope.launch {
            _authScreenState.value = AuthScreenState.Register(phoneNumber = phone, isLoading = true)
            val result = registerUseCase(phone, name, username)
            log("register: $result")
            _authScreenState.value =
                AuthScreenState.Register(phoneNumber = phone, isLoading = false)

            if (result.isSuccess && result.getOrNull() != null) {
                _authScreenState.value = AuthScreenState.Success
            }
            else {
                val exception = result.exceptionOrNull()
                _authScreenState.value = AuthScreenState.Register(
                    phoneNumber = phone,
                    isError = true,
                    isLoading = false,
                    errorMessage = exception?.message.ifNullOrBlank { DEFAULT_ERROR_MESSAGE }
                )
            }
        }
    }
    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Что-то пошло не так"
    }
}

