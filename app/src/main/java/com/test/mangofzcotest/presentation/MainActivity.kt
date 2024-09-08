package com.test.mangofzcotest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.mangofzcotest.utils.navigatePopUpSelf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.Auth.route) {
                composable(Screen.Auth.route) {
                    AuthScreen {
                        navController.navigatePopUpSelf(Screen.Profile.route)
                    }
                }
                composable(Screen.Chats.route) {
//                    ChatListScreen(navController)
                }
                composable(Screen.Profile.route) {
                    ProfileScreen {
                        navController.navigate(Screen.EditProfile.route)
                    }
                }
                composable(Screen.EditProfile.route) {
                    EditProfileScreen(onProfileUpdated = navController::popBackStack)
                }
            }
        }
    }
}