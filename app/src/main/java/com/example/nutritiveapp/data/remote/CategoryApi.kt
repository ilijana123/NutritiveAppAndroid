package com.example.nutritiveapp.data.remote

import com.example.nutritiveapp.data.model.Category
import retrofit2.http.GET

interface CategoryApi {
    @GET("categories")
    suspend fun getAllCategories(): List<Category>
}