package com.artmcar.wrarchive.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    background = backgroundLight,
    surfaceContainer = surfaceContainerLight,
    secondaryContainer = secondaryContainerLight,
    surface = surfaceContainerLight


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
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
    val customTextColors: Color,
    val customErrorColors: Color,
    val customCardBackgroundColors: Color
)
val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        customButtonColors = Color.Unspecified,
        customTextColors = Color.Unspecified,
        customErrorColors = Color.Unspecified,
        customCardBackgroundColors = Color.Unspecified
    )
}
object ExtendedTheme {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current
}