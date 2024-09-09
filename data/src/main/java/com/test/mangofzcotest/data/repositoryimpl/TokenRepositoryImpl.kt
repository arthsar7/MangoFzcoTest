package com.test.mangofzcotest.data.repositoryimpl

import com.test.mangofzcotest.data.network.apiservice.RefreshTokenApiService
import com.test.mangofzcotest.data.network.dto.request.RefreshTokenRequest
import com.test.mangofzcotest.data.network.dto.response.TokenResponse
import com.test.mangofzcotest.data.utils.safeApiCall
import com.test.mangofzcotest.data.utils.toDomain
import com.test.mangofzcotest.domain.entities.TokenData
import com.test.mangofzcotest.domain.repository.TokenRepository
import com.test.mangofzcotest.domain.storage.PreferencesManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val apiService: RefreshTokenApiService,
    private val prefs: PreferencesManager
): TokenRepository {
    override suspend fun refreshAccessToken(): Result<TokenData> = safeApiCall {
        apiService.refreshToken(RefreshTokenRequest(prefs.refreshToken.first()))
    }.onSuccess { prefs.saveTokens(it.accessToken.orEmpty(), it.refreshToken.orEmpty())}
        .map(TokenResponse::toDomain)
}