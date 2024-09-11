package com.test.mangofzcotest.presentation.navigation.screen

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.test.mangofzcotest.R
import com.test.mangofzcotest.presentation.navigation.screen.Screen.AuthGraph.CodeInput.homeBottomItems
import com.test.mangofzcotest.presentation.navigation.screen.Screen.AuthGraph.CodeInput.homeGraphChildren

private val profileBottomItem = BottomItem(
    icon = Icons.Filled.Person,
    titleRes = R.string.profile,
    route = "profile"
)

private val chatsBottomItem = BottomItem(
    icon = Icons.AutoMirrored.Filled.Send,
    titleRes = R.string.chats,
    route = "chats"
)

sealed class Graph(val route: String, val bottomItems: List<BottomItem> = listOf())

sealed class Screen(
    val route: String,
    val bottomItem: BottomItem? = null
) {
    protected val homeBottomItems = listOf(profileBottomItem, chatsBottomItem)

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

        data object Chats : Screen("chats", chatsBottomItem)
        data object Profile : Screen("profile", profileBottomItem)
        data object EditProfile : Screen("edit_profile", profileBottomItem)

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

data class BottomItem(
    val icon: ImageVector,
    @StringRes val titleRes: Int,
    val route: String
)