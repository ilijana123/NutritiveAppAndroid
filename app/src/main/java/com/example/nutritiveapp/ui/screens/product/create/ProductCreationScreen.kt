package com.example.nutritiveapp.ui.screens.product.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nutritiveapp.viewmodels.ProductCreationViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCreationScreen(
    onBack: () -> Unit,
    onProductCreated: () -> Unit,
    initialBarcode: String? = null,
    viewModel: ProductCreationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(initialBarcode) {
        if (!initialBarcode.isNullOrEmpty()) {
            viewModel.updateBarcode(initialBarcode)
        }
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            Toast.makeText(context, "✅ Product created successfully!", Toast.LENGTH_SHORT).show()
            onProductCreated()
        }
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.createProduct() },
                icon = { Icon(Icons.Default.Check, "Create") },
                text = { Text("Create Product") },
                containerColor = Color(0xFF4CAF50)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SectionHeader("Basic Information")
            }

            item {
                OutlinedTextField(
                    value = uiState.barcode,
                    onValueChange = viewModel::updateBarcode,
                    label = { Text("Barcode") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            item {
                OutlinedTextField(
                    value = uiState.productName,
                    onValueChange = viewModel::updateProductName,
                    label = { Text("Product Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                SectionHeader("Nutrition Information (per 100g)")
            }

            item {
                NutritionInputGrid(
                    uiState = uiState,
                    onValueChange = viewModel::updateNutritionValue
                )
            }

            item {
                SectionHeader("Categories")
            }

            item {
                SelectionChipRow(
                    items = uiState.availableCategories,
                    selectedIds = uiState.selectedCategoryIds,
                    onToggle = viewModel::toggleCategorySelection,
                    getName = { it.name },
                    getId = { it.id }
                )
            }

            item {
                SectionHeader("Allergens")
            }

            item {
                SelectionChipRow(
                    items = uiState.availableAllergens,
                    selectedIds = uiState.selectedAllergenIds,
                    onToggle = viewModel::toggleAllergenSelection,
                    getName = { it.name },
                    getId = { it.id }
                )
            }

            item {
                SectionHeader("Tags")
            }

            item {
                SelectionChipRow(
                    items = uiState.availableTags,
                    selectedIds = uiState.selectedTagIds,
                    onToggle = viewModel::toggleTagSelection,
                    getName = { it.name },
                    getId = { it.id }
                )
            }

            item {
                SectionHeader("Countries")
            }

            item {
                SelectionChipRow(
                    items = uiState.availableCountries,
                    selectedIds = uiState.selectedCountryIds,
                    onToggle = viewModel::toggleCountrySelection,
                    getName = { it.name },
                    getId = { it.id }
                )
            }

            item { SectionHeader("Additives") }
            item {
                SelectionChipRow(
                    items = uiState.availableAdditives,
                    selectedIds = uiState.selectedAdditiveIds,
                    onToggle = viewModel::toggleAdditiveSelection,
                    getName = { it.name },
                    getId = { it.id }
                )
            }

            item {
                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.error?.let { error ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = error,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        }
    }
}