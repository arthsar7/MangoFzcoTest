package com.test.mangofzcotest.presentation.base.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
inline fun <reified T : ViewModel> NavController.sharedViewModel(
    parentRoute: String? = currentBackStackEntry?.destination?.route,
    navBackStackEntry: NavBackStackEntry
): T = parentRoute?.let {
    val parentEntry = remember(navBackStackEntry) {
        getBackStackEntry(it)
    }
    hiltViewModel(parentEntry)
} ?: hiltViewModel()