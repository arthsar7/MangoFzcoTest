package com.test.mangofzcotest.data.repositoryimpl


import com.test.mangofzcotest.data.network.apiservice.ApiService
import com.test.mangofzcotest.data.network.dto.request.AuthCodeResponse
import com.test.mangofzcotest.data.network.dto.request.CodeRequest
import com.test.mangofzcotest.data.network.dto.request.PhoneRequest
import com.test.mangofzcotest.data.network.dto.request.RegisterRequest
import com.test.mangofzcotest.data.network.dto.response.LoginOutResponse
import com.test.mangofzcotest.data.network.dto.response.TokenResponse
import com.test.mangofzcotest.data.utils.safeApiCall
import com.test.mangofzcotest.data.utils.toDomain
import com.test.mangofzcotest.domain.repository.AuthRepository
import com.test.mangofzcotest.domain.storage.PreferencesManager
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val prefs: PreferencesManager
) : AuthRepository {

    override suspend fun checkAuthCode(phone: String, code: String) = safeApiCall {
        apiService.checkAuthCode(CodeRequest(phone = phone, code = code))
    }.onSuccess {
        if (it.isUserExists) {
            prefs.saveTokens(it.accessToken.orEmpty(), it.refreshToken.orEmpty())
            prefs.saveUserId(it.userId)
        }
    }
        .map(LoginOutResponse::toDomain)


    override suspend fun sendAuthCode(phone: String) = safeApiCall {
        apiService.sendAuthCode(PhoneRequest(phone))
    }.map(AuthCodeResponse::isSuccess)

    override suspend fun register(phone: String, name: String, username: String) = safeApiCall {
        apiService.registerUser(
            RegisterRequest(
                phone = phone,
                name = name,
                username = username
            )
        )
    }.onSuccess {
        prefs.saveTokens(it.accessToken.orEmpty(), it.refreshToken.orEmpty())
        prefs.saveUserId(it.userId)
    }
        .map(TokenResponse::toDomain)
}
