package com.test.mangofzcotest.data.repositoryimpl


import com.test.mangofzcotest.data.network.apiservice.ApiService
import com.test.mangofzcotest.data.network.dto.request.CodeRequest
import com.test.mangofzcotest.data.network.dto.request.PhoneRequest
import com.test.mangofzcotest.data.network.dto.request.RegisterRequest
import com.test.mangofzcotest.data.network.dto.response.LoginOutResponse
import com.test.mangofzcotest.domain.repository.AuthRepository
import com.test.mangofzcotest.domain.storage.PreferencesManager
import com.test.mangofzcotest.utils.toModel
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val prefs: PreferencesManager
) : AuthRepository {

    override suspend fun checkAuthCode(phone: String, code: String) = runCatching {
        val response = apiService.checkAuthCode(CodeRequest(phone = phone, code = code))
        if (response.isSuccessful) {
            val loginOutResponse: LoginOutResponse? = response.body()
            if (loginOutResponse != null) {
                prefs.saveTokens(loginOutResponse.accessToken.orEmpty(), loginOutResponse.refreshToken.orEmpty())
                loginOutResponse.toModel()
            }
            else {
                throw Exception(response.message())
            }
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun sendAuthCode(phone: String) = runCatching {
        val response = apiService.sendAuthCode(PhoneRequest(phone))
        if (response.isSuccessful) {
            response.body()?.isSuccess == true
        }
        else {
            throw Exception(response.message())
        }
    }

    override suspend fun register(phone: String, name: String, username: String) = runCatching {
        val response = apiService.registerUser(
            RegisterRequest(
                phone = phone,
                name = name,
                username = username
            )
        )
        if (response.isSuccessful) {
            val tokenResponse = response.body()
            if (tokenResponse != null) {
                prefs.saveTokens(tokenResponse.accessToken.orEmpty(), tokenResponse.refreshToken.orEmpty())
                tokenResponse.toModel()
            }
            else {
                throw Exception(response.message())
            }
        }
        else {
            throw Exception(response.message())
        }
    }
}
