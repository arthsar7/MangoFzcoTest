package com.test.mangofzcotest.utils

import com.test.mangofzcotest.data.network.dto.response.LoginOutResponse
import com.test.mangofzcotest.data.network.dto.response.TokenResponse
import com.test.mangofzcotest.data.network.dto.response.UploadImage
import com.test.mangofzcotest.data.network.dto.response.UserProfileResponse
import com.test.mangofzcotest.data.network.dto.response.UserUpdateRequest
import com.test.mangofzcotest.domain.entities.LoginData
import com.test.mangofzcotest.domain.entities.TokenData
import com.test.mangofzcotest.domain.entities.UserProfileData
import com.test.mangofzcotest.domain.entities.UserUpdateData

fun LoginOutResponse.toModel() = LoginData(
    accessToken = accessToken.orEmpty(),
    refreshToken = refreshToken.orEmpty(),
    userId = userId,
    isUserExists = isUserExists
)

fun TokenResponse.toModel() = TokenData(
    accessToken = accessToken.orEmpty(),
    refreshToken = refreshToken.orEmpty(),
    userId = userId
)

fun UserProfileResponse.toModel() = UserProfileData(
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
    bigAvatar = profileData.avatarsResponse?.bigAvatar,
    miniAvatar = profileData.avatarsResponse?.miniAvatar
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