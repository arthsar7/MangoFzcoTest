package com.test.mangofzcotest.domain.entities

data class UserProfileData(
    val name: String,
    val username: String,
    val birthday: String?,
    val city: String?,
    val vk: String?,
    val instagram: String?,
    val status: String?,
    val avatar: String?,
    val id: Int,
    val last: String?,
    val online: Boolean,
    val created: String?,
    val phone: String,
    val completedTask: Int,
    val bigAvatar: String?,
    val miniAvatar: String?
)