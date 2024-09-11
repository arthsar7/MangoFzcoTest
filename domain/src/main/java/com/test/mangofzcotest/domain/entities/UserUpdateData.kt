package com.test.mangofzcotest.domain.entities


data class UserUpdateData(
    val name: String,
    val username: String,
    val birthday: String?,
    val city: String?,
    val status: String?,
    val avatarFileName: String?,
    val avatarBase64: String?,
    val vk: String?,
    val instagram: String?,
)