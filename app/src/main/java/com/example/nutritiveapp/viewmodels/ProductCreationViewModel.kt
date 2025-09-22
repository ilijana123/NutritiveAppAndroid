package com.example.nutritiveapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiveapp.data.model.request.CreateProductRequest
import com.example.nutritiveapp.data.repository.ProductCreationRepository
import com.example.nutritiveapp.data.repository.AllergenRepository
import com.example.nutritiveapp.data.repository.CategoryRepository
import com.example.nutritiveapp.data.repository.TagRepository
import com.example.nutritiveapp.data.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductCreationViewModel @Inject constructor(
    private val productCreationRepository: ProductCreationRepository,
    private val allergenRepository: AllergenRepository,
    private val categoryRepository: CategoryRepository,
    private val tagRepository: TagRepository,
    private val countryRepository: CountryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductCreationUiState())
    val uiState: StateFlow<ProductCreationUiState> = _uiState.asStateFlow()

    init {
        loadAllOptions()
    }

    private fun loadAllOptions() {
        viewModelScope.launch {
            try {
                val allergens = allergenRepository.getAllAllergens()
                val categories = categoryRepository.getAllCategories()
                val tags = tagRepository.getAllTags()
                val countries = countryRepository.getAllCountries()

                _uiState.value = _uiState.value.copy(
                    availableAllergens = allergens,
                    availableCategories = categories,
                    availableTags = tags,
                    availableCountries = countries
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load options: ${e.message}"
                )
            }
        }
    }

    fun updateBarcode(barcode: String) {
        _uiState.value = _uiState.value.copy(barcode = barcode)
    }

    fun updateProductName(name: String) {
        _uiState.value = _uiState.value.copy(productName = name)
    }

    fun updateNutritionValue(field: String, value: String) {
        _uiState.value = when (field) {
            "carbohydrates" -> _uiState.value.copy(carbohydrates = value)
            "carbohydrates100g" -> _uiState.value.copy(carbohydrates100g = value)
            "energy" -> _uiState.value.copy(energy = value)
            "energyKj100g" -> _uiState.value.copy(energyKj100g = value)
            "fat" -> _uiState.value.copy(fat = value)
            "fat100g" -> _uiState.value.copy(fat100g = value)
            "fiber" -> _uiState.value.copy(fiber = value)
            "fiber100g" -> _uiState.value.copy(fiber100g = value)
            "proteins" -> _uiState.value.copy(proteins = value)
            "proteins100g" -> _uiState.value.copy(proteins100g = value)
            "salt" -> _uiState.value.copy(salt = value)
            "salt100g" -> _uiState.value.copy(salt100g = value)
            "saturatedFat" -> _uiState.value.copy(saturatedFat = value)
            "saturatedFat100g" -> _uiState.value.copy(saturatedFat100g = value)
            "sodium" -> _uiState.value.copy(sodium = value)
            "sodium100g" -> _uiState.value.copy(sodium100g = value)
            "sugars" -> _uiState.value.copy(sugars = value)
            "sugars100g" -> _uiState.value.copy(sugars100g = value)
            else -> _uiState.value
        }
    }

    fun toggleAllergenSelection(allergenId: Long) {
        val currentList = _uiState.value.selectedAllergenIds.toMutableList()
        if (currentList.contains(allergenId)) {
            currentList.remove(allergenId)
        } else {
            currentList.add(allergenId)
        }
        _uiState.value = _uiState.value.copy(selectedAllergenIds = currentList)
    }

    fun toggleCategorySelection(categoryId: Long) {
        val currentList = _uiState.value.selectedCategoryIds.toMutableList()
        if (currentList.contains(categoryId)) {
            currentList.remove(categoryId)
        } else {
            currentList.add(categoryId)
        }
        _uiState.value = _uiState.value.copy(selectedCategoryIds = currentList)
    }

    fun toggleTagSelection(tagId: Long) {
        val currentList = _uiState.value.selectedTagIds.toMutableList()
        if (currentList.contains(tagId)) {
            currentList.remove(tagId)
        } else {
            currentList.add(tagId)
        }
        _uiState.value = _uiState.value.copy(selectedTagIds = currentList)
    }

    fun toggleCountrySelection(countryId: Long) {
        val currentList = _uiState.value.selectedCountryIds.toMutableList()
        if (currentList.contains(countryId)) {
            currentList.remove(countryId)
        } else {
            currentList.add(countryId)
        }
        _uiState.value = _uiState.value.copy(selectedCountryIds = currentList)
    }

    fun toggleAdditiveSelection(additiveId: Long) {
        val currentList = _uiState.value.selectedAdditiveIds.toMutableList()
        if (currentList.contains(additiveId)) {
            currentList.remove(additiveId)
        } else {
            currentList.add(additiveId)
        }
        _uiState.value = _uiState.value.copy(selectedAdditiveIds = currentList)
    }

    fun createProduct() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val request = CreateProductRequest(
                    barcode = _uiState.value.barcode,
                    name = _uiState.value.productName,
                    carbohydrates = _uiState.value.carbohydrates.toDoubleOrNull(),
                    carbohydrates_100g = _uiState.value.carbohydrates100g.toDoubleOrNull(),
                    energy = _uiState.value.energy.toDoubleOrNull(),
                    energy_kj_100g = _uiState.value.energyKj100g.toDoubleOrNull(),
                    fat = _uiState.value.fat.toDoubleOrNull(),
                    fat_100g = _uiState.value.fat100g.toDoubleOrNull(),
                    fiber = _uiState.value.fiber.toDoubleOrNull(),
                    fiber_100g = _uiState.value.fiber100g.toDoubleOrNull(),
                    proteins = _uiState.value.proteins.toDoubleOrNull(),
                    proteins_100g = _uiState.value.proteins100g.toDoubleOrNull(),
                    salt = _uiState.value.salt.toDoubleOrNull(),
                    salt_100g = _uiState.value.salt100g.toDoubleOrNull(),
                    saturated_fat = _uiState.value.saturatedFat.toDoubleOrNull(),
                    saturated_fat_100g = _uiState.value.saturatedFat100g.toDoubleOrNull(),
                    sodium = _uiState.value.sodium.toDoubleOrNull(),
                    sodium_100g = _uiState.value.sodium100g.toDoubleOrNull(),
                    sugars = _uiState.value.sugars.toDoubleOrNull(),
                    sugars_100g = _uiState.value.sugars100g.toDoubleOrNull(),
                    allergenIds = _uiState.value.selectedAllergenIds,
                    additiveIds = _uiState.value.selectedAdditiveIds,
                    tagIds = _uiState.value.selectedTagIds,
                    categoryIds = _uiState.value.selectedCategoryIds,
                    countryIds = _uiState.value.selectedCountryIds
                )

                productCreationRepository.createProduct(request)
                    .onSuccess {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isSuccess = true
                        )
                    }
                    .onFailure { error ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "Failed to create product: ${error.message}"
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to create product: ${e.message}"
                )
            }
        }
    }
}