package com.test.mangofzcotest.presentation.navigation.screen

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.test.mangofzcotest.R

sealed class BottomItem(
    val icon: ImageVector,
    @StringRes val titleRes: Int,
    val route: String
) {
    data object Profile : BottomItem(Icons.Filled.Person, R.string.profile, "profile")
    data object Chats : BottomItem(Icons.AutoMirrored.Filled.Send, R.string.chats, "chats")
}