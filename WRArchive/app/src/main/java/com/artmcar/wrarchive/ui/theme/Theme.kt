package com.artmcar.wrarchive.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    secondary = primaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    background = backgroundDark,
    surfaceContainer = surfaceContainerDark,
    secondaryContainer = secondaryContainerDark,
    surface = surfaceContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    onPrimary = onPrimaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    secondary = primaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    background = backgroundLight,
    surfaceContainer = surfaceContainerLight,
    secondaryContainer = secondaryContainerLight,
    surface = surfaceContainerLight
)

@Composable
fun WRArchiveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme

    val customColors = if(darkTheme) DarkCustomColors else LightCustomColors

    CompositionLocalProvider(LocalCustomColors provides customColors){
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}


@Immutable
data class CustomColors(
    val customButtonColors: Color,
    val customButtonTextPrimaryColors: Color,
    val customButtonTextSecondaryColors: Color,
    val customErrorColors: Color,
    val customCardBackgroundColors: Color,
    val customDarkAddEditButtonTextColors: Color,
    val customDarkAddEditButtonColors: Color
)
val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        customButtonColors = Color.Unspecified,
        customButtonTextPrimaryColors = Color.Unspecified,
        customButtonTextSecondaryColors = Color.Unspecified,
        customErrorColors = Color.Unspecified,
        customCardBackgroundColors = Color.Unspecified,
        customDarkAddEditButtonTextColors = Color.Unspecified,
        customDarkAddEditButtonColors = Color.Unspecified
    )
}
object ExtendedTheme {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current
}