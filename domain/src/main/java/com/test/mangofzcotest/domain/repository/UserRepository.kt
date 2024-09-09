package com.test.mangofzcotest.domain.repository

import com.test.mangofzcotest.domain.entities.UserProfileData
import com.test.mangofzcotest.domain.entities.UserUpdateData

interface UserRepository {

    suspend fun getUserProfile(): Result<UserProfileData>

    suspend fun updateUserProfile(profileUpdate: UserUpdateData): Result<Unit>

}
