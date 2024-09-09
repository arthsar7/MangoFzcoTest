package com.test.mangofzcotest.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun ToastMessage(
    message: String,
    duration: Int = Toast.LENGTH_SHORT,
    context: Context = LocalContext.current
) {
    LaunchedEffect(message) {
        Toast.makeText(context, message, duration).show()
    }
}