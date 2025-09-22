package com.example.nutritiveapp.data.dto

data class ProductDetailsDto(
    val barcode: String,
    val name: String? = null,
    val imageUrl: String? = null,
    val nutriments: NutrimentDTO? = null,
    val nutriscore: NutriscoreDTO? = null,
    val additives: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val countries: List<String> = emptyList(),
    val allergens: List<String> = emptyList(),
    val categories: List<String> = emptyList()
)
