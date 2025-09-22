package com.example.nutritiveapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White

val DarkGreen = Color(0xFF064E3B)
val GreenGray = Color(0xFF4ADE80)
val LightGreenWhite = Color(0xFFE6F4EA)
val MediumGreen = Color(0xFF16A34A)
val SoftGreenGray = Color(0xFF64748B)

val ColorScheme.focusedTextFieldText
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White else DarkGreen

val ColorScheme.unfocusedTextFieldText
    @Composable
    get() = if (isSystemInDarkTheme()) GreenGray else Color(0xFF065F46)

val ColorScheme.textFieldContainer
    @Composable
    get() = if (isSystemInDarkTheme()) DarkGreen.copy(alpha = 0.6f) else White
