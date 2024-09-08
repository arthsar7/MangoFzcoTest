package com.test.mangofzcotest.data.network.apiservice

import com.test.mangofzcotest.data.network.dto.request.AuthCodeResponse
import com.test.mangofzcotest.data.network.dto.request.CodeRequest
import com.test.mangofzcotest.data.network.dto.request.PhoneRequest
import com.test.mangofzcotest.data.network.dto.request.RegisterRequest
import com.test.mangofzcotest.data.network.dto.response.LoginOutResponse
import com.test.mangofzcotest.data.network.dto.response.TokenResponse
import com.test.mangofzcotest.data.network.dto.response.UpdateUserProfileResponse
import com.test.mangofzcotest.data.network.dto.response.UserProfileResponse
import com.test.mangofzcotest.data.network.dto.response.UserUpdateRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("users/send-auth-code/")
    suspend fun sendAuthCode(@Body phoneRequest: PhoneRequest): Response<AuthCodeResponse>

    @POST("users/check-auth-code/")
    suspend fun checkAuthCode(@Body codeRequest: CodeRequest): Response<LoginOutResponse>

    @POST("users/register/")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<TokenResponse>

    @GET("users/me/")
    suspend fun getUserProfile(): Response<UserProfileResponse>

    @PUT("users/me/")
    suspend fun updateUserProfile(
        @Body userUpdateRequest: UserUpdateRequest
    ): Response<UpdateUserProfileResponse>
}