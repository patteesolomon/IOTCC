package org.example.project

// In theme/Theme.kt
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val colors = DarkColorScheme
    MaterialTheme(
        colorScheme = colors,
        // ... define typography and shapes
        content = content
    )
}