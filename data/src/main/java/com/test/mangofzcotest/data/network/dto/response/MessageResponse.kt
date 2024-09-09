package com.test.mangofzcotest.data.network.dto.response

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("message") val message: String
)
