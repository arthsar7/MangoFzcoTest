package com.test.mangofzcotest.presentation.navigation.auth.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.domain.usecases.auth.RegisterUseCase
import com.test.mangofzcotest.presentation.BaseViewModel
import com.test.mangofzcotest.presentation.navigation.screen.Screen
import com.test.mangofzcotest.presentation.navigation.screen.ScreenState
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
            _screenState.value = ScreenState.Loading()
            registerUseCase(phone, name, username).onSuccess {
                _screenState.value = ScreenState.Success(phone)
            }.handleFailure()
        }
    }

}