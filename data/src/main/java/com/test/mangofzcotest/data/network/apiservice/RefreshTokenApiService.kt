package com.test.mangofzcotest.data.network.apiservice

import com.test.mangofzcotest.data.network.dto.request.RefreshTokenRequest
import com.test.mangofzcotest.data.network.dto.response.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenApiService {

    @POST("users/refresh-token/")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Response<TokenResponse>

}