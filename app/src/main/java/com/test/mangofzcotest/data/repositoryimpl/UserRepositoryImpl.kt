package com.test.mangofzcotest.data.repositoryimpl

import com.test.mangofzcotest.data.network.apiservice.ApiService
import com.test.mangofzcotest.domain.entities.UserProfileData
import com.test.mangofzcotest.domain.entities.UserUpdateData
import com.test.mangofzcotest.domain.repository.UserRepository
import com.test.mangofzcotest.utils.toDto
import com.test.mangofzcotest.utils.toModel
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
): UserRepository {
    override suspend fun getUserProfile(): Result<UserProfileData> = runCatching {
        val response = apiService.getUserProfile()
        if (response.isSuccessful) {
            response.body()!!.toModel()
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun updateUserProfile(profileUpdate: UserUpdateData) = runCatching {
        val response = apiService.updateUserProfile(profileUpdate.toDto())
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
    }

}