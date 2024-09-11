package com.test.mangofzcotest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.test.mangofzcotest.presentation.navigation.home.BottomNavigationBar
import com.test.mangofzcotest.presentation.navigation.host.MainNavHost
import com.test.mangofzcotest.presentation.navigation.screen.Screen
import com.test.mangofzcotest.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route

                val shouldShowBottomBar = Screen.shouldShowBottomBar(currentRoute)

                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomBar) {
                            BottomNavigationBar(navController)
                        }
                    }
                ) { paddingValues ->
                    MainNavHost(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController
                    )
                }
            }

        }
    }
}