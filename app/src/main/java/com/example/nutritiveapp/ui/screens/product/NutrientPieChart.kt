package com.example.nutritiveapp.ui.screens.product

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.nutritiveapp.data.model.PieSegment
import com.example.nutritiveapp.ui.model.NutrimentsUi

@Composable
fun NutrientPieChart(
    nutriments: NutrimentsUi,
    modifier: Modifier = Modifier
) {
    val energy = nutriments.energy?.extractNumber() ?: 0f
    val sugars = nutriments.sugars?.extractNumber() ?: 0f
    val fat = nutriments.fat?.extractNumber() ?: 0f
    val proteins = nutriments.proteins?.extractNumber() ?: 0f
    val salt = nutriments.salt?.extractNumber() ?: 0f

    val total = energy + sugars + fat + proteins + salt

    if (total > 0) {
        val segments = listOf(
            PieSegment("Energy", energy, Color(0xFFFF9800)),
            PieSegment("Sugars", sugars, Color(0xFFE91E63)),
            PieSegment("Fat", fat, Color(0xFF795548)),
            PieSegment("Proteins", proteins, Color(0xFFD32F2F)),
            PieSegment("Salt", salt, Color(0xFF9E9E9E))
        ).filter { it.value > 0 }

        Canvas(modifier = modifier) {
            drawPieChart(segments, total)
        }
    }
}