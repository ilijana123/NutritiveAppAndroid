package com.example.nutritiveapp.data.repository

import com.example.nutritiveapp.data.model.Category
import com.example.nutritiveapp.data.remote.CategoryApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryApi: CategoryApi
) {
    suspend fun getAllCategories(): List<Category> {
        return try {
            categoryApi.getAllCategories()
        } catch (e: Exception) {
            emptyList()
        }
    }
}