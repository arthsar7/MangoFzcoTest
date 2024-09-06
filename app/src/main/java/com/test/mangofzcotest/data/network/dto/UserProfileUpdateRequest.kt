package com.test.mangofzcotest.data.network.dto

data class UserProfileUpdateRequest(
    val name: String,
    val city: String,
    val birthDate: String,
    val zodiacSign: String,
    val about: String
)