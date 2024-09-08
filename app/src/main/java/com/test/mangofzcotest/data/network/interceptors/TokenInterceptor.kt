package com.test.mangofzcotest.data.network.interceptors

import com.test.mangofzcotest.domain.storage.PreferencesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val preferencesManager: PreferencesManager
) : Interceptor {

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_PREFIX = "Bearer "
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        val token = runBlocking { preferencesManager.accessToken.first() }
        if (token.isNotEmpty()) {
            requestBuilder.header(HEADER_AUTHORIZATION, "$TOKEN_PREFIX$token")
        }
        return chain.proceed(requestBuilder.build())
    }

}