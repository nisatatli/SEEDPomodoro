package com.example.seedpomodoro.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme

// 🌱 Light Color Palette
private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,

    secondary = GreenDark,
    onSecondary = Color.White,

    background = Color(0xFFF6F7F8),
    onBackground = Color(0xFF1C1B1F),

    surface = Color.White,
    onSurface = Color(0xFF1C1B1F)
)

// 🌙 Dark Color Palette (opsiyonel ama hazır)
private val DarkColorScheme = darkColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.Black,

    secondary = GreenDark,
    onSecondary = Color.Black,

    background = Color(0xFF121212),
    onBackground = Color.White,

    surface = Color(0xFF1E1E1E),
    onSurface = Color.White
)

@Composable
fun SeedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
