package com.example.nutritiveapp.data.repository

import com.example.nutritiveapp.data.dto.AllergenDTO
import com.example.nutritiveapp.data.remote.AllergenApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AllergenRepository @Inject constructor(
    private val allergenApi: AllergenApi
) {
    suspend fun getAllAllergens(): List<AllergenDTO> {
        return try {
            allergenApi.getAllAllergens()
        } catch (e: Exception) {
            emptyList()
        }
    }
}