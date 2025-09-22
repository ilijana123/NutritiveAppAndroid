package com.example.nutritiveapp.ui.screens.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiveapp.data.remote.RetrofitInstance
import com.example.nutritiveapp.viewmodels.ProductDetailsViewModel

@Composable
fun ProductDetailsScreen(
    barcode: String,
    onBack: () -> Unit,
    viewModel: ProductDetailsViewModel = viewModel {
        ProductDetailsViewModel(RetrofitInstance.productApi)
    }
) {
    val uiState = viewModel.uiState

    LaunchedEffect(barcode) {
        viewModel.loadProduct(barcode)
    }

    Scaffold { padding ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: ${uiState.errorMessage}",
                        color = Color.Red
                    )
                }
            }

            uiState.product != null -> {
                val product = uiState.product

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    ProductCard(
                        product = product,
                        userAllergenNames = emptySet(), // TODO: inject actual user allergens
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F8F0))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Nutritional Breakdown",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            product.nutriments?.let { nutriments ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        nutriments.energy?.takeIf { it.isNotBlank() }?.let {
                                            NutrientRow("Energy", it, "⚡", Color(0xFFFF9800))
                                        }
                                        nutriments.sugars?.takeIf { it.isNotBlank() }?.let {
                                            NutrientRow("Sugars", it, "🟡", Color(0xFFE91E63))
                                        }
                                        nutriments.fat?.takeIf { it.isNotBlank() }?.let {
                                            NutrientRow("Fat", it, "🟤", Color(0xFF795548))
                                        }
                                        nutriments.proteins?.takeIf { it.isNotBlank() }?.let {
                                            NutrientRow("Proteins", it, "🔴", Color(0xFFD32F2F))
                                        }
                                        nutriments.salt?.takeIf { it.isNotBlank() }?.let {
                                            NutrientRow("Salt", it, "⚪", Color(0xFF9E9E9E))
                                        }
                                    }

                                    NutrientPieChart(
                                        nutriments = nutriments,
                                        modifier = Modifier.size(120.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
