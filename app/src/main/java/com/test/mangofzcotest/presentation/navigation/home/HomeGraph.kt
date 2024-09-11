package com.test.mangofzcotest.presentation.navigation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.test.mangofzcotest.presentation.base.viewmodel.sharedViewModel
import com.test.mangofzcotest.presentation.navigation.home.chat.ChatListScreen
import com.test.mangofzcotest.presentation.navigation.home.chat.CurrentChatScreen
import com.test.mangofzcotest.presentation.navigation.home.profile.edit.EditProfileScreen
import com.test.mangofzcotest.presentation.navigation.home.profile.main.ProfileScreen
import com.test.mangofzcotest.presentation.navigation.screen.Screen.HomeGraph
import com.text.mangofzcotest.core.utils.navigateSingleTop

fun NavGraphBuilder.homeGraph(
    navController: NavHostController
) {
    navigation(HomeGraph.Profile.route, HomeGraph.route) {

        composable(HomeGraph.Chats.route) {
            ChatListScreen {
                navController.navigateSingleTop(HomeGraph.CurrentChat.createRoute(it.id))
            }
        }

        composable(HomeGraph.Profile.route) { navBackStackEntry ->
            ProfileScreen(
                viewModel = navController.sharedViewModel(
                    parentRoute = HomeGraph.Profile.route,
                    navBackStackEntry = navBackStackEntry
                ),
            ) {
                navController.navigateSingleTop(HomeGraph.EditProfile.route)
            }
        }

        composable(HomeGraph.EditProfile.route) { navBackStackEntry ->
            EditProfileScreen(
                onProfileUpdated = navController::navigateUp,
                viewModel = navController.sharedViewModel(
                    parentRoute = HomeGraph.Profile.route,
                    navBackStackEntry = navBackStackEntry
                )
            )
        }

        composable(
            route = HomeGraph.CurrentChat.route,
            arguments = HomeGraph.CurrentChat.args
        ) {
            CurrentChatScreen()
        }
    }
}