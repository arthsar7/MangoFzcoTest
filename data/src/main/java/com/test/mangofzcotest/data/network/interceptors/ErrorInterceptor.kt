package com.test.mangofzcotest.data.network.interceptors

import com.google.gson.Gson
import com.test.mangofzcotest.data.network.dto.response.HTTPValidationError
import com.text.mangofzcotest.core.exceptions.UnsuccessfulResponseException
import com.text.mangofzcotest.core.exceptions.ValidationException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ErrorInterceptor @Inject constructor(private val gson: Gson): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (!response.isSuccessful) {
            if (response.code == 422) {
                val error = response.body?.string()?.let {
                    gson.fromJson(it, HTTPValidationError::class.java)
                }
                val errorMessage = error?.detail?.firstOrNull()?.message.orEmpty()
                throw ValidationException(errorMessage)
            }
            throw UnsuccessfulResponseException(response.message)
        }
        return response
    }
}