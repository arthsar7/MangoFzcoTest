package com.test.mangofzcotest.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.rassvet.ui.theme.ScreenSize

object Theme {

    val textFieldColors: AppTextFieldColors
        @Composable
        get() = LocalAppTextFieldColors.current

    val iconButtonColors: AppIconButtonColors
        @Composable
        get() = LocalAppIconButtonColors.current

    val colors: AppPalette
        @Composable
        get() = LocalAppPalette.current

    val buttonColors: AppButtonColors
        @Composable
        get() = LocalAppButtonColors.current

    val typography: AppTypography
        @Composable
        get() = LocalAppTypography.current

    val shapes: AppShapes
        @Composable
        get() = LocalAppShapes.current
}

@Composable
fun AppTheme(
    systemUiController: SystemUiController = rememberSystemUiController(),
    widthDp: Int = LocalConfiguration.current.screenWidthDp,
    content: @Composable () -> Unit,
) {
    val screenSize = when {
        widthDp <= 320 -> ScreenSize.SMALL
        widthDp <= 620 -> ScreenSize.MEDIUM
        else -> ScreenSize.EXPAND
    }
    val appTypography = AppThemeImpl.getTypography(screenSize)
    SideEffect {
        systemUiController.apply {
            setNavigationBarColor(
                color = Color.Transparent,
                darkIcons = true,
                navigationBarContrastEnforced = false
            )
            setStatusBarColor(
                color = Color.Transparent,
                darkIcons = true
            )
        }
    }
    CompositionLocalProvider(
        LocalAppPalette provides AppThemeImpl.appPalette,
        LocalAppButtonColors provides AppThemeImpl.appButtonColors,
        LocalAppTypography provides appTypography,
        LocalScreenSize provides screenSize,
        LocalAppTextFieldColors provides AppThemeImpl.appTextFieldColors,
        LocalAppShapes provides AppThemeImpl.appShapes,
        LocalAppIconButtonColors provides AppThemeImpl.appIconButtonColors,
        content = content
    )
}

private val LocalAppPalette = staticCompositionLocalOf<AppPalette> {
    error("No AppPalette provided")
}

private val LocalAppButtonColors = staticCompositionLocalOf<AppButtonColors> {
    error("No AppPalette provided")
}

private val LocalAppTypography = staticCompositionLocalOf<AppTypography> {
    error("No AppTypography provided")
}

private val LocalScreenSize = staticCompositionLocalOf<ScreenSize> {
    error("No ScreenSize provided")
}

private val LocalAppTextFieldColors = staticCompositionLocalOf<AppTextFieldColors> {
    error("No AppTextFieldColors provided")
}

private val LocalAppShapes = staticCompositionLocalOf<AppShapes> {
    error("No AppShapes provided")
}

private val LocalAppIconButtonColors = staticCompositionLocalOf<AppIconButtonColors> {
    error("No AppIconButtonColors provided")
}