package com.test.mangofzcotest.data.network.dto.response

import com.google.gson.annotations.SerializedName

data class ValidationError(
    @SerializedName("loc") val loc: List<String>,
    @SerializedName("msg") val message: String,
    @SerializedName("type") val type: String
)

data class HTTPValidationError(
    @SerializedName("detail") val detail: List<ValidationError>?
)
