package com.test.mangofzcotest.data.repositoryimpl

import com.test.mangofzcotest.data.network.apiservice.ApiService
import com.test.mangofzcotest.data.network.dto.response.UserProfileResponse
import com.test.mangofzcotest.data.utils.safeApiCall
import com.test.mangofzcotest.data.utils.toDomain
import com.test.mangofzcotest.data.utils.toDto
import com.test.mangofzcotest.domain.entities.UserProfileData
import com.test.mangofzcotest.domain.entities.UserUpdateData
import com.test.mangofzcotest.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
): UserRepository {
    override suspend fun getUserProfile(): Result<UserProfileData> {
        return safeApiCall(apiService::getUserProfile).map(UserProfileResponse::toDomain)
    }

    override suspend fun updateUserProfile(profileUpdate: UserUpdateData) = safeApiCall {
        apiService.updateUserProfile(profileUpdate.toDto())
    }.map { }

}