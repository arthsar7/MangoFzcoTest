package com.test.mangofzcotest.data.network.dto.response

import com.google.gson.annotations.SerializedName

data class UserUpdateRequest(
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("avatar") val avatar: UploadImage?,
    @SerializedName("vk") val vk: String?,
    @SerializedName("instagram") val instagram: String?
)

data class UploadImage(
    @SerializedName("filename") val filename: String,
    @SerializedName("base_64") val base64: String
)
