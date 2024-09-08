package com.test.mangofzcotest.domain.entities

data class LoginData(
    val refreshToken: String,
    val accessToken: String,
    val userId: Int,
    val isUserExists: Boolean
)