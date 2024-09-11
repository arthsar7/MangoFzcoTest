package com.test.mangofzcotest.presentation.navigation.host

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.test.mangofzcotest.presentation.navigation.auth.authGraph
import com.test.mangofzcotest.presentation.navigation.home.BottomNavigationBar
import com.test.mangofzcotest.presentation.navigation.home.homeGraph
import com.test.mangofzcotest.presentation.navigation.screen.Screen
import com.test.mangofzcotest.presentation.theme.Theme

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController()
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val shouldShowBottomBar = Screen.shouldShowBottomBar(currentRoute)

    val enterTransition = if (shouldShowBottomBar) {
        EnterTransition.None
    } else {
        fadeIn(animationSpec = tween(500))
    }

    val exitTransition = if (shouldShowBottomBar) {
        ExitTransition.None
    } else {
        fadeOut(animationSpec = tween(500))
    }
    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .background(Theme.colors.background)
                .imePadding(),
            navController = navController,
            startDestination = Screen.AuthGraph.route,
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
        ) {
            authGraph(navController)
            homeGraph(paddingValues, navController)
        }
    }
}