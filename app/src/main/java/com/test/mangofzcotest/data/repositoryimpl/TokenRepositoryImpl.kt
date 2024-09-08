package com.test.mangofzcotest.data.repositoryimpl

import com.test.mangofzcotest.data.network.apiservice.RefreshTokenApiService
import com.test.mangofzcotest.data.network.dto.request.RefreshTokenRequest
import com.test.mangofzcotest.domain.entities.TokenData
import com.test.mangofzcotest.domain.repository.TokenRepository
import com.test.mangofzcotest.domain.storage.PreferencesManager
import com.test.mangofzcotest.utils.toModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val apiService: RefreshTokenApiService,
    private val prefs: PreferencesManager
): TokenRepository {
    override suspend fun refreshAccessToken(): Result<TokenData> = runCatching {
        val response = apiService.refreshToken(RefreshTokenRequest(prefs.refreshToken.first()))
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