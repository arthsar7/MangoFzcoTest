package com.test.mangofzcotest.data.network.authenticator

import com.test.mangofzcotest.data.network.interceptors.TokenInterceptor
import com.test.mangofzcotest.domain.storage.PreferencesManager
import com.test.mangofzcotest.domain.usecases.auth.RefreshAccessTokenUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val refreshAccessTokenUseCase: RefreshAccessTokenUseCase
) : Authenticator {

    private val currentAccessToken get() = runBlocking { preferencesManager.accessToken.first() }

    override fun authenticate(route: Route?, response: Response) = synchronized(this) {
        if (response.code == 401) {
            val requestBuilder = response.request.newBuilder()
            if (isRefreshNeeded(response)) {
                runBlocking { refreshAccessTokenUseCase() }
            }
            return@synchronized requestBuilder
                .header(
                    name = TokenInterceptor.HEADER_AUTHORIZATION,
                    value = "${TokenInterceptor.TOKEN_PREFIX}$currentAccessToken"
                )
                .build()
        }
        else {
            return@synchronized response.request
        }
    }

    private fun isRefreshNeeded(response: Response): Boolean {
        val oldAccessToken = response.request.header(TokenInterceptor.HEADER_AUTHORIZATION).orEmpty()

        val isTokensEqual = currentAccessToken == oldAccessToken

        return isTokensEqual || currentAccessToken.isBlank()
    }
}