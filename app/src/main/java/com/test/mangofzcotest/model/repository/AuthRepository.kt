package com.test.mangofzcotest.model.repository

interface AuthRepository {

    suspend fun authenticate(phone: String, code: String)

    suspend fun register(phone: String, name: String, username: String)

}