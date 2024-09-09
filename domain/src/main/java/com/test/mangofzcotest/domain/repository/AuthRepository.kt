package com.test.mangofzcotest.domain.repository

import com.test.mangofzcotest.domain.entities.LoginData
import com.test.mangofzcotest.domain.entities.TokenData

interface AuthRepository {

    suspend fun sendAuthCode(phone: String): Result<Boolean>

    suspend fun register(phone: String, name: String, username: String): Result<TokenData>

    suspend fun checkAuthCode(phone: String, code: String): Result<LoginData>

}