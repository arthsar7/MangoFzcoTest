package com.test.mangofzcotest.data.utils


import com.text.mangofzcotest.core.exceptions.EmptyBodyException
import com.text.mangofzcotest.core.exceptions.UnsuccessfulResponseException
import com.text.mangofzcotest.core.utils.log
import retrofit2.Response

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
    return runCatching {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body() ?: throw EmptyBodyException()
        } else {
            val errorBody = response.errorBody()?.string()
            throw UnsuccessfulResponseException(errorBody ?: response.message())
        }
    }.onFailure { throwable -> log("OkHttp", throwable) }
}