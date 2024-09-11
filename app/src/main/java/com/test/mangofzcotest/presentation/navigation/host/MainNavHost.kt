package com.test.mangofzcotest.presentation.navigation.host

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.test.mangofzcotest.presentation.navigation.auth.authGraph
import com.test.mangofzcotest.presentation.navigation.home.homeGraph
import com.test.mangofzcotest.presentation.navigation.screen.Screen
import com.test.mangofzcotest.presentation.theme.Theme

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    shouldShowBottomBar: Boolean,
    navController: NavHostController = rememberNavController()
) {

    val enterTransition = if (shouldShowBottomBar) {
        EnterTransition.None
    }
    else {
        fadeIn(animationSpec = tween(500))
    }

    val exitTransition = if (shouldShowBottomBar) {
        ExitTransition.None
    }
    else {
        fadeOut(animationSpec = tween(500))
    }

    NavHost(
        modifier = modifier
            .background(Theme.colors.background)
            .imePadding(),
        navController = navController,
        startDestination = Screen.AuthGraph.route,
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
    ) {
        authGraph(navController)
        homeGraph(navController)
    }
}