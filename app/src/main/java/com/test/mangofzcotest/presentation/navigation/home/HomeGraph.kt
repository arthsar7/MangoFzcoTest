package com.test.mangofzcotest.presentation.navigation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.test.mangofzcotest.presentation.base.viewmodel.sharedViewModel
import com.test.mangofzcotest.presentation.navigation.home.chat.ChatListScreen
import com.test.mangofzcotest.presentation.navigation.home.profile.edit.EditProfileScreen
import com.test.mangofzcotest.presentation.navigation.home.profile.main.ProfileScreen
import com.test.mangofzcotest.presentation.navigation.screen.Screen
import com.text.mangofzcotest.core.utils.navigateSingleTop

fun NavGraphBuilder.homeGraph(
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    navigation(Screen.HomeGraph.Profile.route, Screen.HomeGraph.route) {
        composable(Screen.HomeGraph.Chats.route) {
            ChatListScreen()
        }
        composable(Screen.HomeGraph.Profile.route) { navBackStackEntry ->
            ProfileScreen(
                viewModel = navController.sharedViewModel(
                    parentRoute = Screen.HomeGraph.Profile.route,
                    navBackStackEntry = navBackStackEntry
                ),
                paddingValues = paddingValues
            ) {
                navController.navigateSingleTop(Screen.HomeGraph.EditProfile.route)
            }
        }
        composable(Screen.HomeGraph.EditProfile.route) { navBackStackEntry ->
            EditProfileScreen(
                paddingValues = paddingValues,
                onProfileUpdated = navController::navigateUp,
                viewModel = navController.sharedViewModel(
                    parentRoute = Screen.HomeGraph.Profile.route,
                    navBackStackEntry = navBackStackEntry
                )
            )
        }
    }
}