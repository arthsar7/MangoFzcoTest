package com.test.mangofzcotest.data.utils

import com.test.mangofzcotest.data.network.dto.response.LoginOutResponse
import com.test.mangofzcotest.data.network.dto.response.TokenResponse
import com.test.mangofzcotest.data.network.dto.response.UploadImage
import com.test.mangofzcotest.data.network.dto.response.UserProfileResponse
import com.test.mangofzcotest.data.network.dto.response.UserUpdateRequest
import com.test.mangofzcotest.domain.entities.LoginData
import com.test.mangofzcotest.domain.entities.TokenData
import com.test.mangofzcotest.domain.entities.UserProfileData
import com.test.mangofzcotest.domain.entities.UserUpdateData
import com.text.mangofzcotest.core.utils.ifNotNullOrBlank

fun LoginOutResponse.toDomain() = LoginData(
    accessToken = accessToken.orEmpty(),
    refreshToken = refreshToken.orEmpty(),
    userId = userId,
    isUserExists = isUserExists
)

fun TokenResponse.toDomain() = TokenData(
    accessToken = accessToken.orEmpty(),
    refreshToken = refreshToken.orEmpty(),
    userId = userId
)
private const val API_SOURCE = "https://plannerok.ru/"

fun UserProfileResponse.toDomain() = UserProfileData(
    name = profileData.name,
    username = profileData.username,
    birthday = profileData.birthday,
    city = profileData.city,
    vk = profileData.vk,
    instagram = profileData.instagram,
    status = profileData.status,
    avatar = profileData.avatar,
    id = profileData.id,
    last = profileData.last,
    online = profileData.online,
    created = profileData.created,
    phone = profileData.phone,
    completedTask = profileData.completedTask,
    bigAvatar = profileData.avatarsResponse?.bigAvatar?.ifNotNullOrBlank { "$API_SOURCE$it" },
    miniAvatar = profileData.avatarsResponse?.miniAvatar?.ifNotNullOrBlank { "$API_SOURCE$it" }
)

fun UserUpdateData.toDto() = UserUpdateRequest(
    name = name,
    username = username,
    birthday = birthday,
    city = city,
    vk = vk,
    status = status,
    avatar = UploadImage(
        base64 = avatarBase64.orEmpty(),
        filename = avatarFileName.orEmpty()
    ),
    instagram = instagram
)