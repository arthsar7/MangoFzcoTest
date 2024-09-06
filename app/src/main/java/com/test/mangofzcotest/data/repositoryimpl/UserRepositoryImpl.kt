package com.test.mangofzcotest.data.repositoryimpl

import com.test.mangofzcotest.data.network.apiservice.ApiService
import com.test.mangofzcotest.data.network.dto.UserProfileUpdateRequest
import com.test.mangofzcotest.model.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): UserRepository {
    override suspend fun getUserProfile() = apiService.getUserProfile("Bearer YOUR_ACCESS_TOKEN")

    override suspend fun updateUserProfile(profileUpdate: UserProfileUpdateRequest) = apiService.updateUserProfile("Bearer YOUR_ACCESS_TOKEN", profileUpdate)
}
