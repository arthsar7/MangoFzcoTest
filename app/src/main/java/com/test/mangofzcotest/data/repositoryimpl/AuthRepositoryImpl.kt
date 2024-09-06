package com.test.mangofzcotest.data.repositoryimpl


import com.test.mangofzcotest.data.network.apiservice.ApiService
import com.test.mangofzcotest.data.network.dto.CodeVerificationRequest
import com.test.mangofzcotest.data.network.dto.RegistrationRequest
import com.test.mangofzcotest.model.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): AuthRepository {
    override suspend fun authenticate(phone: String, code: String) = apiService.checkAuthCode(
        CodeVerificationRequest(phone, code)
    )

    override suspend fun register(phone: String, name: String, username: String) = apiService.register(
        RegistrationRequest(phone, name, username)
    )
}
