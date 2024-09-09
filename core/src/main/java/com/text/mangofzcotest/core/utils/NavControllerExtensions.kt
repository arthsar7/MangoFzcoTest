package com.text.mangofzcotest.core.utils

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

fun NavHostController.navigateSingleTop(route: String, builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route) {
        launchSingleTop = true
        builder()
    }
}

fun NavHostController.navigatePopUpSelf(route: String) {
    val currentRoute = currentBackStackEntry?.destination?.route ?: return
    navigateSingleTop(route) {
        popUpTo(currentRoute) { inclusive = true }
    }
}
