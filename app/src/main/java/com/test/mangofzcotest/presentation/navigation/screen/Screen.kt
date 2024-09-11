package com.test.mangofzcotest.presentation.navigation.screen

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.test.mangofzcotest.presentation.navigation.screen.Screen.AuthGraph.CodeInput.homeBottomItems
import com.test.mangofzcotest.presentation.navigation.screen.Screen.AuthGraph.CodeInput.homeGraphChildren


sealed class Graph(val route: String, val bottomItems: List<BottomItem> = listOf())

sealed class Screen(
    val route: String,
    val bottomItem: BottomItem? = null
) {
    protected val homeBottomItems = listOf(BottomItem.Profile, BottomItem.Chats)

    protected val homeGraphChildren = listOf(
        HomeGraph.Chats,
        HomeGraph.Profile,
        HomeGraph.EditProfile
    )

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

    data object HomeGraph : Graph("home_graph", homeBottomItems) {

        private const val ARG_CHAT_ID = "chat_id"

        data object Chats : Screen("chats", BottomItem.Chats)
        data object Profile : Screen("profile", BottomItem.Profile)
        data object EditProfile : Screen("edit_profile", BottomItem.Profile)
        data object CurrentChat : Screen("current_chat/{${ARG_CHAT_ID}}") {

            fun createRoute(chatId: Int) = "current_chat/$chatId"

            val args = listOf(
                navArgument(ARG_CHAT_ID) { type = NavType.IntType }
            )

            fun getChatId(savedStateHandle: SavedStateHandle): Int {
                return checkNotNull(savedStateHandle[ARG_CHAT_ID])
            }

        }

        fun getScreen(currentRoute: String?): Screen? {
            return homeGraphChildren.firstOrNull { it.route == currentRoute }
        }
    }

    companion object {
        fun shouldShowBottomBar(currentRoute: String?): Boolean {
            return currentRoute in homeGraphChildren.map { it.route }
        }
    }
}