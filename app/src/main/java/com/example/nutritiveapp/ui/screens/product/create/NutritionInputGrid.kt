package com.example.nutritiveapp.ui.screens.product.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.nutritiveapp.viewmodels.ProductCreationUiState

@Composable
fun NutritionInputGrid(
    uiState: ProductCreationUiState,
    onValueChange: (String, String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        NutritionInputRow("Energy (kJ)", uiState.energy, "energy", onValueChange)
        NutritionInputRow("Carbohydrates (g)", uiState.carbohydrates, "carbohydrates", onValueChange)
        NutritionInputRow("Sugars (g)", uiState.sugars, "sugars", onValueChange)
        NutritionInputRow("Fat (g)", uiState.fat, "fat", onValueChange)
        NutritionInputRow("Saturated Fat (g)", uiState.saturatedFat, "saturatedFat", onValueChange)
        NutritionInputRow("Proteins (g)", uiState.proteins, "proteins", onValueChange)
        NutritionInputRow("Salt (g)", uiState.salt, "salt", onValueChange)
        NutritionInputRow("Fiber (g)", uiState.fiber, "fiber", onValueChange)
    }
}