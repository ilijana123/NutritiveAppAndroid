package com.example.nutritiveapp.ui.model

data class ProductUi(
    val barcode: String,
    val title: String,
    val imageUrl: String?,
    val categories: List<String>,
    val additives: List<String>,
    val nutriments: NutrimentsUi?,
    val countries: List<String>,
    val allergenNames: List<String>,
    val tagBadges: List<TagBadgeUi>,
)