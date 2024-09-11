package com.test.mangofzcotest.presentation.base.ui

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.test.mangofzcotest.presentation.theme.Theme

@Composable
fun PrimaryCircularProgressIndicator() {
    CircularProgressIndicator(color = Theme.colors.primary)
}