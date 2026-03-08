package com.vulancube.lqxgb.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimary,
    secondary = BlueAccent,
    tertiary = GoldAccent,
    background = BlueBackground,
    surface = BlueSurface,
    onPrimary = WhiteText,
    onSecondary = WhiteText,
    onTertiary = BlueDark,
    onBackground = WhiteText,
    onSurface = WhiteText
)

@Composable
fun VulanCubeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
