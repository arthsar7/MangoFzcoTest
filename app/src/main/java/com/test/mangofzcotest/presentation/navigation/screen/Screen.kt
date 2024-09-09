package com.test.mangofzcotest.presentation.navigation.screen

sealed class Graph(val route: String)

sealed class Screen(val route: String) {
    data object AuthGraph : Graph("auth_graph") {
        data object PhoneInput : Screen("phone_input")
        data object CodeInput : Screen("code_input")
        data object Register : Screen("register")
    }
    data object HomeGraph : Graph("home_graph") {
        data object Chats : Screen("chats")
        data object Profile : Screen("profile")
        data object EditProfile : Screen("edit_profile")
    }
}