package com.food.delivery.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun AppTheme(
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            surfaceVariant = Color.White,
            background = BackgroundColor
        ),
        typography = Typography,
        content = content
    )
}