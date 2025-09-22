package com.example.nutritiveapp.data.repository

import com.example.nutritiveapp.data.dto.ProductDetailsDto
import com.example.nutritiveapp.data.model.request.CreateProductRequest
import com.example.nutritiveapp.data.remote.ProductApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductCreationRepository @Inject constructor(
    private val productApi: ProductApi
) {
    suspend fun createProduct(request: CreateProductRequest): Result<ProductDetailsDto> {
        return try {
            val response = productApi.createProduct(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}