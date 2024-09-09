package com.test.mangofzcotest.presentation.navigation.host

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.test.mangofzcotest.presentation.navigation.graph.authGraph
import com.test.mangofzcotest.presentation.navigation.graph.homeGraph
import com.test.mangofzcotest.presentation.navigation.screen.Screen
import com.test.mangofzcotest.presentation.theme.Theme

@Composable
fun MainNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        modifier = Modifier
            .background(Theme.colors.background)
            .imePadding(),
        navController = navController,
        startDestination = Screen.AuthGraph.route,
    ) {
        authGraph(navController)
        homeGraph(navController)
    }
}