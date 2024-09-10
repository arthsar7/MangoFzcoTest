package com.test.mangofzcotest.presentation.navigation.profile

import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.domain.entities.UserProfileData
import com.test.mangofzcotest.domain.entities.UserUpdateData
import com.test.mangofzcotest.domain.usecases.user.GetUserProfileDataUseCase
import com.test.mangofzcotest.domain.usecases.user.UpdateUserProfileUseCase
import com.test.mangofzcotest.presentation.BaseViewModel
import com.test.mangofzcotest.presentation.navigation.screen.ScreenState
import com.text.mangofzcotest.core.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileDataUseCase: GetUserProfileDataUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : BaseViewModel<UserProfileData>() {

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading()
            getUserProfileDataUseCase().onSuccess { profileData ->
               _screenState.value = ScreenState.Success(profileData)
            }.handleFailure()
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
            _screenState.value = ScreenState.Loading()
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

            val result = updateUserProfileUseCase(updatedProfile)

            result.onSuccess { userProfileData ->
                log(userProfileData.toString())
                onProfileUpdated()
                _screenState.value = ScreenState.Success(userProfileData)
            }.handleFailure()
        }
    }

}
