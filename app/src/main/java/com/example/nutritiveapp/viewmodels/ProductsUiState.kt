package com.example.nutritiveapp.viewmodels

import com.example.nutritiveapp.data.dto.ProductDetailsDto

data class ProductsUiState(
    val products: List<ProductDetailsDto> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
    val hasMore: Boolean = true,
    val currentPage: Int = 0,
    val selectedAllergens: List<Long> = emptyList(),
    val selectedAdditives: List<Long> = emptyList(),
    val selectedCountries: List<Long> = emptyList(),
    val selectedCategories: List<Long> = emptyList(),
    val selectedTags: List<Long> = emptyList(),
    val searchTerm: String = ""
)