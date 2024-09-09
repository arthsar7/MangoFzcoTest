package com.test.mangofzcotest.data.network.dto.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("phone") val phone: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String
)
