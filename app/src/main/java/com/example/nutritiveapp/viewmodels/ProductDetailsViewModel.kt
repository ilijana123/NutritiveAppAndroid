package com.example.nutritiveapp.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiveapp.data.dto.ProductDetailsDto
import com.example.nutritiveapp.data.remote.ProductApi
import com.example.nutritiveapp.ui.mapper.toUi
import com.example.nutritiveapp.ui.model.ProductUi
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val productApi: ProductApi
) : ViewModel() {

    companion object {
        private const val TAG = "ProductDetailsViewModel"
    }

    var uiState by mutableStateOf(ProductDetailsUiState())
        private set

    fun loadProduct(barcode: String) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true, errorMessage = null)

                val product: ProductDetailsDto = productApi.getProduct(barcode)
                uiState = uiState.copy(product = product.toUi())

                Log.d(TAG, "✅ Product loaded for $barcode")

                try {
                    val resp = productApi.checkAllergens(barcode)
                    if (resp.isSuccessful) {
                        Log.d(TAG, "⚠️ Allergen check triggered for $barcode")
                    } else {
                        Log.w(TAG, "⚠️ Allergen check failed: ${resp.code()} ${resp.message()}")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "💥 Error calling checkAllergens", e)
                }

                //fetchComparableProducts(barcode)

            } catch (e: Exception) {
                Log.e(TAG, "💥 Error loading product", e)
                uiState = uiState.copy(errorMessage = e.message)
            } finally {
                uiState = uiState.copy(isLoading = false)
            }
        }
    }

//    private fun fetchComparableProducts(barcode: String) {
//        viewModelScope.launch {
//            try {
//                val resp = RetrofitInstance.spoonacularApi.getComparableProducts(
//                    barcode,
//                    "cbf31f1280ab458ea9ae312fd9174e53"
//                )
//
////                uiState = uiState.copy(
////                    comparableProducts = mapOf(
////                        "Higher Protein" to resp.comparableProducts.protein.map { it.toUi() },
////                        "Better Score" to resp.comparableProducts.spoonacularScore.map { it.toUi() },
////                        "Lower Sugar" to resp.comparableProducts.sugar.map { it.toUi() }
////                    )
////                )
//
//            } catch (e: Exception) {
//                Log.e(TAG, "💥 Error fetching comparable products", e)
//            }
//        }
//    }

    fun clearError() {
        uiState = uiState.copy(errorMessage = null)
    }
}
