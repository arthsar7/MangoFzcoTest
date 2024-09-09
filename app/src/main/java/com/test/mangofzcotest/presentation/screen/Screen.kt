package com.test.mangofzcotest.presentation.screen

sealed class Screen(val route: String) {
    data object Auth : Screen("auth")
    data object Chats : Screen("chats")
    data object Profile : Screen("profile")
    data object EditProfile : Screen("edit_profile")
}