package com.test.mangofzcotest.presentation.navigation.auth

import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.domain.usecases.auth.RegisterUseCase
import com.test.mangofzcotest.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthRegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel() {

    fun register(phone: String, name: String, username: String) {
        viewModelScope.launch {
            mutableScreenState.value = ScreenState.Loading
            val result = registerUseCase(phone, name, username)

            if (result.isSuccess && result.getOrNull() != null) {
                mutableScreenState.value = ScreenState.Success
            }
            else {
                val exception = result.exceptionOrNull()
                mutableScreenState.value = ScreenState.Error(exception.errorMessage)
            }
        }
    }

}