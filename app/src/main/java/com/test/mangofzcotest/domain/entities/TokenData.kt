package com.test.mangofzcotest.domain.entities

data class TokenData(
    val refreshToken: String,
    val accessToken: String,
    val userId: Int
)
