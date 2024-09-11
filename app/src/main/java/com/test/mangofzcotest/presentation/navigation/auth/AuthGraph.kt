package com.test.mangofzcotest.presentation.navigation.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.test.mangofzcotest.presentation.navigation.auth.codeinput.AuthCodeInputScreen
import com.test.mangofzcotest.presentation.navigation.auth.phoneinput.AuthPhoneInputScreen
import com.test.mangofzcotest.presentation.navigation.auth.register.AuthRegisterScreen
import com.test.mangofzcotest.presentation.navigation.screen.Screen.AuthGraph
import com.test.mangofzcotest.presentation.navigation.screen.Screen.HomeGraph
import com.text.mangofzcotest.core.utils.navigatePopUpGraph
import com.text.mangofzcotest.core.utils.navigateSingleTop

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(AuthGraph.PhoneInput.route, AuthGraph.route) {
        composable(AuthGraph.PhoneInput.route) {
            AuthPhoneInputScreen { phone ->
                navController.navigateSingleTop(AuthGraph.CodeInput.createRoute(phone))
            }
        }

        composable(
            route = AuthGraph.CodeInput.route,
            arguments = AuthGraph.CodeInput.args
        ) {
            AuthCodeInputScreen(
                onRegister = { phone ->
                    navController.navigateSingleTop(AuthGraph.Register.createRoute(phone)) {
                        // чтобы при нажатии на кнопку "Назад" вернулась на phone input
                        popUpTo(AuthGraph.PhoneInput.route)
                    }
                },
                onSuccess = {
                    navController.navigatePopUpGraph(HomeGraph.route)
                }
            )
        }

        composable(
            route = AuthGraph.Register.route,
            arguments = AuthGraph.Register.args
        ) {
            AuthRegisterScreen(
                onSuccess = {
                    navController.navigatePopUpGraph(HomeGraph.route)
                }
            )
        }
    }
}