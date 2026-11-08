package com.kopinyemil.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val CoffeeBrown = Color(0xFF6F4E37)
val DarkCoffee = Color(0xFF3E2723)
val Cream = Color(0xFFF5E6CC)
val Beige = Color(0xFFE8D5B7)
val WarmWhite = Color(0xFFFFFAF3)
val Charcoal = Color(0xFF2B2320)
val SuccessGreen = Color(0xFF4E8F5A)

private val AppColorScheme = lightColorScheme(
    primary = DarkCoffee,
    onPrimary = Color.White,
    primaryContainer = Beige,
    onPrimaryContainer = DarkCoffee,
    secondary = CoffeeBrown,
    onSecondary = Color.White,
    background = WarmWhite,
    onBackground = Charcoal,
    surface = Color.White,
    onSurface = Charcoal,
    surfaceVariant = Cream,
    onSurfaceVariant = CoffeeBrown,
    outline = Color(0xFFBFA58A)
)

@Composable
fun KopiDanNyemilTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = AppColorScheme, content = content)
}
