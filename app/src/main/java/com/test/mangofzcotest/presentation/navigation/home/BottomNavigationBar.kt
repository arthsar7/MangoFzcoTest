package com.test.mangofzcotest.presentation.navigation.home

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.test.mangofzcotest.presentation.navigation.screen.Screen.HomeGraph
import com.test.mangofzcotest.presentation.theme.Theme
import com.text.mangofzcotest.core.utils.navigateSingleTop

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        containerColor = Theme.colors.background,
    ) {
        val currentRoute = navController.currentDestination?.route
        HomeGraph.bottomItems.forEach {
            NavigationBarItem(
                icon = { Icon(it.icon, contentDescription = stringResource(it.titleRes)) },
                label = { Text(stringResource(it.titleRes)) },
                selected = HomeGraph.getScreen(currentRoute)?.bottomItem == it,
                onClick = {
                    navController.navigateSingleTop(it.route) {
                        restoreState = true
                        popUpTo(HomeGraph.route) {
                            saveState = true
                        }
                    }
                }
            )
        }
    }
}