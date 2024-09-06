package com.test.mangofzcotest.model.repository

import com.test.mangofzcotest.data.network.dto.UserProfile
import com.test.mangofzcotest.data.network.dto.UserProfileUpdateRequest

interface UserRepository {

    suspend fun getUserProfile(): UserProfile

    suspend fun updateUserProfile(profileUpdate: UserProfileUpdateRequest)

}
