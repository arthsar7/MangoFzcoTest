package com.test.mangofzcotest.data.network.dto.request

import com.google.gson.annotations.SerializedName

data class AuthCodeResponse(
    @SerializedName("is_success") val isSuccess: Boolean
)
