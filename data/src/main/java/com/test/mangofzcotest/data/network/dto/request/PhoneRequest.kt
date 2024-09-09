package com.test.mangofzcotest.data.network.dto.request

import com.google.gson.annotations.SerializedName

data class PhoneRequest(
    @SerializedName("phone") val phone: String
)
