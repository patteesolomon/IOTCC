package org.example.project

// In theme/Color.kt
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import org.example.project.LightColorScheme

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    secondary = Color(0xFF03DAC5),
    // ... define other light colors
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF609DFF),
    onPrimary = Color.Black,
    secondary = Color(0xFF1E70FD),
    // ... define other dark colors
)

val MongoColorScheme = lightColorScheme(
    primary = Color(0x32C4FFAA),
    onPrimary = Color.White,
    secondary = Color(0xFF72FF50)
)