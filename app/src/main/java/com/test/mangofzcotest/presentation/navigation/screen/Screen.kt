package com.test.mangofzcotest.presentation.navigation.screen

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Graph(val route: String)

sealed class Screen(val route: String) {
    data object AuthGraph : Graph("auth_graph") {
        private const val ARG_PHONE = "phone"
        data object PhoneInput : Screen("phone_input")
        data object CodeInput : Screen("code_input/{${ARG_PHONE}}") {
            fun createRoute(phone: String) = "code_input/$phone"

            val args = listOf(
                navArgument(ARG_PHONE) { type = NavType.StringType }
            )

            fun getPhone(savedStateHandle: SavedStateHandle): String {
                return checkNotNull(savedStateHandle[ARG_PHONE])
            }
        }

        data object Register : Screen("register/{${ARG_PHONE}}") {
            fun createRoute(phone: String) = "register/$phone"

            val args = listOf(
                navArgument(ARG_PHONE) { type = NavType.StringType }
            )

            fun getPhone(savedStateHandle: SavedStateHandle): String {
                return checkNotNull(savedStateHandle[ARG_PHONE])
            }
        }
    }
    data object HomeGraph : Graph("home_graph") {
        data object Chats : Screen("chats")
        data object Profile : Screen("profile")
        data object EditProfile : Screen("edit_profile")
    }
}