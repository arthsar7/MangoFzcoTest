package com.test.mangofzcotest.data.network.interceptors

import com.google.gson.Gson
import com.test.mangofzcotest.data.network.dto.response.HTTPValidationError
import com.test.mangofzcotest.exceptions.ValidationException
import com.test.mangofzcotest.utils.log
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException
import javax.inject.Inject

class ErrorInterceptor @Inject constructor(private val gson: Gson): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (!response.isSuccessful) {
            if (response.code == 422) {
                val error = response.body?.string()?.let {
                    log(it)
                    gson.fromJson(it, HTTPValidationError::class.java)
                }
                log(error.toString())
                val errorMessage = error?.detail?.firstOrNull()?.message.orEmpty()
                throw ValidationException(errorMessage)
            }
            throw IOException(response.message)
        }
        return response
    }
}