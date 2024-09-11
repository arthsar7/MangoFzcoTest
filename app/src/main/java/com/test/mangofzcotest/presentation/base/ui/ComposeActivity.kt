package com.test.mangofzcotest.presentation.base.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

val activity: Activity
    @Composable
    get() {
        val context = LocalContext.current
        return remember { context as Activity }
    }