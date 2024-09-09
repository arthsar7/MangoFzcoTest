package com.test.mangofzcotest.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.test.mangofzcotest.presentation.navigation.profile.edit.EditProfileScreen
import com.test.mangofzcotest.presentation.navigation.profile.main.ProfileScreen
import com.test.mangofzcotest.presentation.navigation.screen.Screen

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation(Screen.HomeGraph.Profile.route, Screen.HomeGraph.route) {
        composable(Screen.HomeGraph.Chats.route) {
            //                    ChatListScreen(navController)
        }
        composable(Screen.HomeGraph.Profile.route) {
            ProfileScreen {
                navController.navigate(Screen.HomeGraph.EditProfile.route)
            }
        }
        composable(Screen.HomeGraph.EditProfile.route) {
            EditProfileScreen(onProfileUpdated = navController::popBackStack)
        }
    }
}