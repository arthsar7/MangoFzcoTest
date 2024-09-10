package com.test.mangofzcotest.domain.usecases.user

import com.test.mangofzcotest.domain.entities.UserProfileData
import com.test.mangofzcotest.domain.entities.UserUpdateData
import com.test.mangofzcotest.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(userUpdateData: UserUpdateData): Result<UserProfileData> {
        return repository.updateUserProfile(userUpdateData)
    }

}