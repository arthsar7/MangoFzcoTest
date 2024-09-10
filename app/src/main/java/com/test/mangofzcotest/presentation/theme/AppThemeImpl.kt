package com.test.mangofzcotest.presentation.theme

import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.test.mangofzcotest.R
import ru.rassvet.ui.theme.ScreenSize

object AppThemeImpl {

    val appPalette = AppPalette(
        background = White,
        onBackground = Blue,
        onBackgroundLight = BlueLight,
        onBackgroundAqua = BlueAqua,
        onPrimary = White,
        primary = AccentPeach,
        onPrimaryAlpha = AccentPeach.copy(alpha = 0.4f),
        buttonGray = BlueAlpha,
        backgroundInfoBox = BlueAlpha,
        secondary = AccentBlue,
        secondaryLight = BgBlue,
        secondaryAqua = AccentBlue.copy(alpha = 0.3f),
        secondaryAlpha7per = AccentBlue.copy(alpha = 0.07f),
        onSecondary = White,
        onSecondaryAlpha = White.copy(alpha = 0.4f),
        buttonOrangeNoActive = Orange.copy(alpha = 0.05f),
        errorColor = Red,
        disabledTextColor = Gray,
        logoGradient = Brush.verticalGradient(
            listOf(
                LogoGradient1,
                LogoGradient1,
                LogoGradient2,
                LogoGradient3,
                LogoGradient4
            )
        )
    )

    val appButtonColors: AppButtonColors
        @Composable get() = AppButtonColors(
            appButtonGray = buttonColors(
                containerColor = appPalette.buttonGray,
                contentColor = appPalette.onBackground,
                disabledContainerColor = appPalette.buttonOrangeNoActive,
                disabledContentColor = appPalette.onPrimaryAlpha
            ),
            appButtonOrange = buttonColors(
                containerColor = appPalette.primary,
                contentColor = appPalette.onPrimary,
                disabledContainerColor = appPalette.buttonOrangeNoActive,
                disabledContentColor = appPalette.onPrimaryAlpha
            ),
            appButtonBlue = buttonColors(
                containerColor = appPalette.secondary,
                contentColor = appPalette.onSecondary,
                disabledContainerColor = appPalette.secondaryAlpha7per,
                disabledContentColor = appPalette.onSecondaryAlpha
            )
        )

    private val appFont = FontFamily(
        Font(R.font.roboto_light, FontWeight.Light),
        Font(R.font.roboto_bold, FontWeight.Bold),
        Font(R.font.roboto_black, FontWeight.Black),
        Font(R.font.roboto_medium, FontWeight.Medium),
        Font(R.font.roboto_regular, FontWeight.Normal),
        Font(R.font.roboto_thin, FontWeight.Thin)
    )

    private val appTextStyle = TextStyle(
        fontFamily = appFont
    )

    private val defaultTypography: AppTypography
        @Composable get() = AppTypography(
            titleAuthScreen = appTextStyle.copy(
                fontSize = 32.sp,
                fontWeight = FontWeight.Light,
                lineHeight = 32.lh(130),
                color = appPalette.onBackground
            ),
            regularAuthScreen = appTextStyle.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 16.lh(130),
                color = appPalette.onBackground
            )
        )

    private val smallTypography: AppTypography
        @Composable get() = AppTypography(
            titleAuthScreen = defaultTypography.titleAuthScreen.copy(
                fontSize = 24.sp,
                lineHeight = 24.lh(130),
            ),
            regularAuthScreen = defaultTypography.regularAuthScreen.copy(
                fontSize = 14.sp,
                lineHeight = 14.lh(130),
            )
        )

    private val expandTypography: AppTypography
        @Composable get() = AppTypography(
            titleAuthScreen = defaultTypography.titleAuthScreen.copy(
                fontSize = 36.sp,
                lineHeight = 36.lh(130)
            ),
            regularAuthScreen = defaultTypography.regularAuthScreen.copy(
                fontSize = 18.sp,
                lineHeight = 18.lh(130)
            )
        )

    @Composable
    fun getTypography(screenSize: ScreenSize): AppTypography =
        when (screenSize) {
            ScreenSize.SMALL -> smallTypography
            ScreenSize.MEDIUM -> defaultTypography
            ScreenSize.EXPAND -> expandTypography
        }
}