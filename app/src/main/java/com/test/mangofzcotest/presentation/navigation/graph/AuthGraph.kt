package com.test.mangofzcotest.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.test.mangofzcotest.presentation.navigation.auth.AuthPhoneInputScreen
import com.test.mangofzcotest.presentation.navigation.screen.Screen
import com.text.mangofzcotest.core.utils.navigatePopUpSelf

fun NavGraphBuilder.authGraph(navHostController: NavHostController) {
    navigation(Screen.AuthGraph.PhoneInput.route, Screen.AuthGraph.route) {
        composable(Screen.AuthGraph.PhoneInput.route) {
            AuthPhoneInputScreen {
                navHostController.navigatePopUpSelf(Screen.HomeGraph.route)
            }
        }

        composable(Screen.AuthGraph.CodeInput.route) {
            //todo
        }

        composable(Screen.AuthGraph.Register.route) {
            //todo
        }
    }
}