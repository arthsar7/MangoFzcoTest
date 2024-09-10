package com.test.mangofzcotest.data.preferencesimpl

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.test.mangofzcotest.domain.storage.PreferencesManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class PreferencesManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
): PreferencesManager {
    companion object {
        private const val DATA_STORE_NAME = "auth_prefs"
        private val Context.dataStore by preferencesDataStore(DATA_STORE_NAME)

        private const val ACCESS_TOKEN_KEY_NAME = "access_token"
        private const val REFRESH_TOKEN_KEY_NAME = "refresh_token"
        private const val USER_ID_KEY_NAME = "user_id"
        private val ACCESS_TOKEN_KEY = stringPreferencesKey(ACCESS_TOKEN_KEY_NAME)
        private val REFRESH_TOKEN_KEY = stringPreferencesKey(REFRESH_TOKEN_KEY_NAME)
        private val USER_ID_KEY = intPreferencesKey("user_id")
    }

    // Получение токена доступа
    override val accessToken: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[ACCESS_TOKEN_KEY].orEmpty()
        }

    // Получение refresh токена
    override val refreshToken: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[REFRESH_TOKEN_KEY].orEmpty()
        }
    override val userId: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY] ?: 0
        }

    override suspend fun save(key: String, value: Any) {
        context.dataStore.edit { preferences ->
            when (value) {
                is String -> preferences[stringPreferencesKey(key)] = value
                is Int -> preferences[intPreferencesKey(key)] = value
                is Float -> preferences[floatPreferencesKey(key)] = value
                is Double -> preferences[doublePreferencesKey(key)] = value
                is Long -> preferences[longPreferencesKey(key)] = value
                is Boolean -> preferences[booleanPreferencesKey(key)] = value
            }
        }
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        save(ACCESS_TOKEN_KEY_NAME, accessToken)
        save(REFRESH_TOKEN_KEY_NAME, refreshToken)
    }

    override suspend fun saveUserId(userId: Int) {
        save(USER_ID_KEY_NAME, userId)
    }

}