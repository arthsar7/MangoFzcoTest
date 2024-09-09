package com.text.mangofzcotest.core.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val FULL_WIDTH = 390f
private const val FULL_HEIGHT = 844f
private const val RELATIVE_SIZE =
    ((FULL_WIDTH + FULL_HEIGHT) / 2)

@Composable
private fun ep(
    dimension: Float,
    configuration: Configuration = LocalConfiguration.current,
): Float {
    val widthDp = configuration.screenWidthDp
    val heightDp = configuration.screenHeightDp
    val relativeScreen = (widthDp.toFloat() + heightDp.toFloat()) / 2
    val relativePercent = relativeScreen / RELATIVE_SIZE
    return dimension * relativePercent
}

val Number.dep: Dp
    @Composable
    get() = ep(dimension = toFloat()).dp

fun Number.lh(percent: Int): TextUnit = (this.toFloat() / 100 * percent).sp