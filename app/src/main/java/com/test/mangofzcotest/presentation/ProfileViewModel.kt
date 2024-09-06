package com.test.mangofzcotest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.data.network.dto.UserProfile
import com.test.mangofzcotest.model.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow<UserProfile?>(null)
    val profileState: StateFlow<UserProfile?> = _profileState

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            val profile = userRepository.getUserProfile()
            _profileState.value = profile
        }
    }
}
