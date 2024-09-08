package com.test.mangofzcotest.domain.storage

import kotlinx.coroutines.flow.Flow

interface PreferencesManager {
    val accessToken: Flow<String>

    val refreshToken: Flow<String>

    suspend fun save(key: String, value: Any)

    suspend fun saveTokens(accessToken: String, refreshToken: String)
}