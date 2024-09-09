package com.test.mangofzcotest.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.domain.entities.UserProfileData
import com.test.mangofzcotest.domain.entities.UserUpdateData
import com.test.mangofzcotest.domain.usecases.user.GetUserProfileDataUseCase
import com.test.mangofzcotest.domain.usecases.user.UpdateUserProfileUseCase
import com.text.mangofzcotest.core.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileDataUseCase: GetUserProfileDataUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow<UserProfileData?>(null)
    val profileState = _profileState.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            getUserProfileDataUseCase().onSuccess { profileData ->
                _profileState.value = profileData
                log("Profile: $profileData")
            }.onFailure { error ->
                log("Error: $error")
            }
        }
    }

    fun updateProfile(
        name: String,
        username: String,
        birthday: String,
        city: String?,
        vk: String?,
        instagram: String?,
        status: String?,
        avatarFileName: String,
        avatarBase64: String,
        onProfileUpdated: () -> Unit
    ) {
        viewModelScope.launch {
            val updatedProfile = UserUpdateData(
                name = name,
                username = username,
                birthday = birthday,
                city = city,
                vk = vk,
                status = status,
                avatarBase64 = avatarBase64,
                instagram = instagram,
                avatarFileName = avatarFileName
            )

            // Make PUT request to update the profile
            val result = updateUserProfileUseCase(updatedProfile)

            result.onSuccess {
                // Profile successfully updated
                onProfileUpdated()
            }
        }
    }

}
