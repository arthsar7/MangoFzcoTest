package com.test.mangofzcotest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.model.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun authenticate(phone: String, code: String) {
        viewModelScope.launch {
            // Perform authentication logic
            val result = authRepository.authenticate(phone, code)
            // Handle result
        }
    }
}
