package com.test.mangofzcotest.presentation.navigation.auth.codeinput

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.domain.usecases.auth.CheckAuthCodeUseCase
import com.test.mangofzcotest.presentation.BaseViewModel
import com.test.mangofzcotest.presentation.navigation.screen.ScreenState
import com.test.mangofzcotest.presentation.navigation.screen.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthCodeInputViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val checkAuthCodeUseCase: CheckAuthCodeUseCase,
) : BaseViewModel<CodeResult>() {

    val phone = Screen.AuthGraph.CodeInput.getPhone(savedStateHandle)

    fun checkAuthCode(
        phone: String,
        code: String
    ) {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading()
            checkAuthCodeUseCase(phone, code).onSuccess { loginData ->
                val isUserExists = loginData.isUserExists
                if (!isUserExists) {
                    _screenState.value = ScreenState.Success(CodeResult.UserNotExists(phone))
                }
                else {
                    _screenState.value = ScreenState.Success(CodeResult.UserExists(phone))
                }
            }.handleFailure()
        }
    }

}