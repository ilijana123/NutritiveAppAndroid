package com.example.nutritiveapp.viewmodels

import com.example.nutritiveapp.data.model.ComparableProduct
import com.example.nutritiveapp.ui.model.ProductUi

data class HomeUiState(
    val currentProduct: ProductUi? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showScanner: Boolean = false,
    val comparableProducts: Map<String, List<ComparableProduct>> = emptyMap()
)
