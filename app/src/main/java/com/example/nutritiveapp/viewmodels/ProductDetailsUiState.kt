package com.example.nutritiveapp.viewmodels

import com.example.nutritiveapp.ui.model.ProductUi

data class ProductDetailsUiState(
    val product: ProductUi? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val comparableProducts: Map<String, List<ProductUi>> = emptyMap()
)