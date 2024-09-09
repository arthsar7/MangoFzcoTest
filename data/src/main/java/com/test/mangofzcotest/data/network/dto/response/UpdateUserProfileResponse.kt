package com.test.mangofzcotest.data.network.dto.response

import com.google.gson.annotations.SerializedName

data class UpdateUserProfileResponse(
    @SerializedName("avatars") val avatarsResponse: AvatarsResponse?
)
