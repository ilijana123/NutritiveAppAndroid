package com.example.nutritiveapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.nutritiveapp.data.remote.AllergenApi
import com.example.nutritiveapp.data.remote.ProductApi
import com.example.nutritiveapp.data.remote.RetrofitInstance.productApi
import kotlinx.coroutines.launch

class HomeViewModel(productApi: ProductApi, allergenApi: AllergenApi) : ViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    var showScanner by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onScanBarcode() {
        showScanner = true
    }

    fun closeScanner() {
        showScanner = false
    }

    fun onBarcodeScanned(
        code: String,
        onNavigateToDetails: (String) -> Unit,
        onNavigateToNotFound: (String) -> Unit
    ) {
        Log.d(TAG, "Barcode scanned: $code")
        closeScanner()

        viewModelScope.launch {
            try {
                val product = productApi.getProduct(code)
                onNavigateToDetails(code)
            } catch (e: Exception) {
                Log.e(TAG, "Product not found: $code", e)
                onNavigateToNotFound(code)
            }
        }
    }


    fun clearError() {
        errorMessage = null
    }
}
