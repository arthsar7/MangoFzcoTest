package com.test.mangofzcotest.domain.repository

import com.test.mangofzcotest.domain.entities.TokenData

interface TokenRepository {

    suspend fun refreshAccessToken(): Result<TokenData>

}