package com.test.mangofzcotest.presentation.navigation.auth.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.domain.usecases.auth.RegisterUseCase
import com.test.mangofzcotest.presentation.base.viewmodel.BaseViewModel
import com.test.mangofzcotest.presentation.navigation.screen.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthRegisterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<String>() {

    val phone = Screen.AuthGraph.Register.getPhone(savedStateHandle)

    fun register(phone: String, name: String, username: String) {
        viewModelScope.launch {
            emitLoading()
            registerUseCase(phone, name, username).onSuccess {
                emitSuccess(phone)
            }.handleFailure()
        }
    }

}