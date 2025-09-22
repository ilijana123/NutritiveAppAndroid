package com.example.nutritiveapp.ui.screens.products

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.nutritiveapp.viewmodels.ProductsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = viewModel(),
    onProductClick: (String) -> Unit
) {
    val products by viewModel.products.collectAsState()
    val loading by viewModel.loading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            products.isEmpty() -> Text(
                "No saved products yet",
                Modifier.align(Alignment.Center),
                color = Color.Green
            )
            else -> LazyColumn {
                items(products) { product ->
                    ProductItem(product = product, onClick = onProductClick)
                }
            }
        }
    }
}
