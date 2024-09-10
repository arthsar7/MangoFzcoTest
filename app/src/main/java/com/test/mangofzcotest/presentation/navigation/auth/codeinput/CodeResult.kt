package com.test.mangofzcotest.presentation.navigation.auth.codeinput

sealed interface CodeResult {
    data class UserExists(val phone: String) : CodeResult
    data class UserNotExists(val phone: String) : CodeResult
}