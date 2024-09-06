package com.test.mangofzcotest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.mangofzcotest.utils.navigatePopUpSelf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "auth") {
                composable("auth") {
                    AuthScreen {
                        navController.navigatePopUpSelf("chats")
                    }
                }
                composable("chats") {
//                    ChatListScreen(navController)
                }
                composable("profile") {
//                    ProfileScreen(navController)
                }
            }
        }
    }
}