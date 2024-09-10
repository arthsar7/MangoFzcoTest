package com.text.mangofzcotest.core.utils

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

fun NavHostController.navigateSingleTop(route: String, builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route) {
        launchSingleTop = true
        builder()
    }
}

fun NavHostController.navigatePopUpGraph(route: String) {
    navigateSingleTop(route) {
        popUpTo(0) { inclusive = true }
    }
}

fun NavHostController.navigatePopUpSelf(route: String) {
    val currentRoute = currentBackStackEntry?.destination?.route ?: return
    navigate(route) {
        popUpTo(currentRoute) { inclusive = true }
    }
}
