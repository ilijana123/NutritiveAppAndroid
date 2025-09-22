package com.example.nutritiveapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    PRODUCTS("products", "Products", Icons.Default.SaveAlt, "Products"),
    HOME("home", "Home", Icons.Default.Home, "Home"),
    ALLERGENS("allergens", "Allergens", Icons.Default.WarningAmber, "Allergens")
}