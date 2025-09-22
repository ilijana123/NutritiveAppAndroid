package com.example.nutritiveapp.viewmodels

import com.example.nutritiveapp.data.dto.AllergenDTO
import com.example.nutritiveapp.data.model.Additive
import com.example.nutritiveapp.data.model.Category
import com.example.nutritiveapp.data.model.Country
import com.example.nutritiveapp.data.model.Tag


data class ProductCreationUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val barcode: String = "",
    val productName: String = "",
    val carbohydrates: String = "",
    val carbohydrates100g: String = "",
    val energy: String = "",
    val energyKj100g: String = "",
    val fat: String = "",
    val fat100g: String = "",
    val fiber: String = "",
    val fiber100g: String = "",
    val proteins: String = "",
    val proteins100g: String = "",
    val salt: String = "",
    val salt100g: String = "",
    val saturatedFat: String = "",
    val saturatedFat100g: String = "",
    val sodium: String = "",
    val sodium100g: String = "",
    val sugars: String = "",
    val sugars100g: String = "",
    val selectedAllergenIds: List<Long> = emptyList(),
    val selectedAdditiveIds: List<Long> = emptyList(),
    val selectedTagIds: List<Long> = emptyList(),
    val selectedCategoryIds: List<Long> = emptyList(),
    val selectedCountryIds: List<Long> = emptyList(),
    val availableAllergens: List<AllergenDTO> = emptyList(),
    val availableAdditives: List<Additive> = emptyList(),
    val availableTags: List<Tag> = emptyList(),
    val availableCategories: List<Category> = emptyList(),
    val availableCountries: List<Country> = emptyList()
)