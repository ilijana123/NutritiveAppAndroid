package com.example.nutritiveapp.data.model

data class Product(
    val barcode: String,
    val brands: String,
    val imageUrl: String?,
    val nutriments: Nutriment?,
    val nutriscore: Nutriscore?,
    val additives: List<Additive> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val countries: List<Country> = emptyList(),
    val allergens: List<Allergen> = emptyList(),
    val categories: List<Category> = emptyList()
)

