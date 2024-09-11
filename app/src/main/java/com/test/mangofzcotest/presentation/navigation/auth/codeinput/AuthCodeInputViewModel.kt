package com.test.mangofzcotest.presentation.navigation.auth.codeinput

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.domain.usecases.auth.CheckAuthCodeUseCase
import com.test.mangofzcotest.presentation.base.viewmodel.BaseViewModel
import com.test.mangofzcotest.presentation.navigation.screen.Screen.AuthGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthCodeInputViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val checkAuthCodeUseCase: CheckAuthCodeUseCase,
) : BaseViewModel<CodeResult>() {

    val phone = AuthGraph.CodeInput.getPhone(savedStateHandle)

    fun checkAuthCode(
        phone: String,
        code: String
    ) {
        viewModelScope.launch {
            emitLoading()
            checkAuthCodeUseCase(phone, code).onSuccess { loginData ->
                val isUserExists = loginData.isUserExists
                if (!isUserExists) {
                    emitSuccess(CodeResult.UserNotExists(phone))
                }
                else {
                    emitSuccess(CodeResult.UserExists(phone))
                }
            }.handleFailure()
        }
    }

}