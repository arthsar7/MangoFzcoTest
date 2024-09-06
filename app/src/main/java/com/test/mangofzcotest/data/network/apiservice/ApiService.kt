package com.test.mangofzcotest.data.network.apiservice

import com.test.mangofzcotest.data.network.dto.AuthRequest
import com.test.mangofzcotest.data.network.dto.CodeVerificationRequest
import com.test.mangofzcotest.data.network.dto.RegistrationRequest
import com.test.mangofzcotest.data.network.dto.UserProfileUpdateRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("users/send-auth-code/")
    suspend fun sendAuthCode(@Body request: AuthRequest): AuthResponse

    @POST("users/check-auth-code/")
    suspend fun checkAuthCode(@Body request: CodeVerificationRequest): AuthResponse

    @POST("users/register/")
    suspend fun register(@Body request: RegistrationRequest): AuthResponse

    @GET("users/me/")
    suspend fun getUserProfile(@Header("Authorization") token: String): UserProfileResponse

    @PUT("users/me/")
    suspend fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body request: UserProfileUpdateRequest
    ): UserProfileResponse
}
