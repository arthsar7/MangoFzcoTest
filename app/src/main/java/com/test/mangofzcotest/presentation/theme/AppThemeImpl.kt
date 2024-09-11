package com.test.mangofzcotest.presentation.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.test.mangofzcotest.R
import ru.rassvet.ui.theme.ScreenSize

object AppThemeImpl {

    val appShapes = AppShapes(
        smallRoundedShapes = RoundedCornerShape(10),
        mediumRoundedShapes = RoundedCornerShape(30),
        largeRoundedShapes = RoundedCornerShape(50),
        circleShape = CircleShape
    )

    val appPalette = AppPalette(
        background = White,
        onBackground = Blue,
        onBackgroundLight = BlueLight,
        onBackgroundAqua = BlueAqua,
        onPrimary = White,
        primary = AccentLightBlue,
        onPrimaryAlpha = AccentLightBlue.copy(alpha = 0.4f),
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

    val appTextFieldColors: AppTextFieldColors
        @Composable get() = AppTextFieldColors(
            baseColors = TextFieldDefaults.colors(
                unfocusedContainerColor = appPalette.secondary,
                focusedContainerColor = appPalette.secondary,
                disabledContainerColor = appPalette.secondary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedPlaceholderColor = appPalette.onSecondaryAlpha,
                focusedPlaceholderColor = appPalette.onSecondaryAlpha,
                cursorColor = appPalette.primary,
                disabledPlaceholderColor = appPalette.onSecondaryAlpha,
                errorPlaceholderColor = appPalette.onSecondaryAlpha,
                focusedTextColor = appPalette.onSecondary,
                disabledTextColor = appPalette.onSecondary,
                unfocusedTextColor = appPalette.onSecondary,
                errorTextColor = appPalette.errorColor,
                errorContainerColor = appPalette.errorColor,
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
                contentColor = appPalette.onPrimary,
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
            titleLarge = appTextStyle.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                lineHeight = 24.lh(130),
                color = appPalette.onBackground
            ),
            bodyRegular = appTextStyle.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 16.lh(130),
                color = appPalette.onBackground
            ),
            textButton = appTextStyle.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 14.lh(130),
                color = appPalette.onPrimary
            ),
            textInput = appTextStyle.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 14.lh(130),
                color = appPalette.onPrimary
            )
        )

    private val smallTypography: AppTypography
        @Composable get() = AppTypography(
            titleLarge = defaultTypography.titleLarge.copy(
                fontSize = 20.sp,
                lineHeight = 20.lh(130),
            ),
            bodyRegular = defaultTypography.bodyRegular.copy(
                fontSize = 14.sp,
                lineHeight = 14.lh(130),
            ),
            textButton = defaultTypography.textButton.copy(
                fontSize = 12.sp,
                lineHeight = 12.lh(130),
            ),
            textInput = defaultTypography.textInput.copy(
                fontSize = 12.sp,
                lineHeight = 12.lh(130),
            )
        )

    private val expandTypography: AppTypography
        @Composable get() = AppTypography(
            titleLarge = defaultTypography.titleLarge.copy(
                fontSize = 28.sp,
                lineHeight = 28.lh(130)
            ),
            bodyRegular = defaultTypography.bodyRegular.copy(
                fontSize = 18.sp,
                lineHeight = 18.lh(130)
            ),
            textButton = defaultTypography.textButton.copy(
                fontSize = 16.sp,
                lineHeight = 16.lh(130)
            ),
            textInput = defaultTypography.textInput.copy(
                fontSize = 16.sp,
                lineHeight = 16.lh(130)
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