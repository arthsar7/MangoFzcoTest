package com.test.mangofzcotest.data.network.dto.response

import com.google.gson.annotations.SerializedName

data class SuccessResponse(
    @SerializedName("is_success") val isSuccess: Boolean
)
