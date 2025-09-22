package com.example.nutritiveapp.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiveapp.data.dto.ProductDetailsDto
import com.example.nutritiveapp.data.remote.RetrofitInstance.productApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<ProductDetailsDto>>(emptyList())
    val products: StateFlow<List<ProductDetailsDto>> = _products

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun loadProducts() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = productApi.getSavedProducts()
                _products.value = response
                Log.d("ProductsViewModel", "Loaded ${response.size} products")
            } catch (e: Exception) {
                Log.e("ProductsViewModel", "Error loading products", e)
            } finally {
                _loading.value = false
            }
        }
    }
}
