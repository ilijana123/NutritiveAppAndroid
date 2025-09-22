package com.example.nutritiveapp.data.remote

import com.example.nutritiveapp.data.dto.ProductDetailsDto
import com.example.nutritiveapp.data.model.request.CreateProductRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApi {
    @GET("products/{barcode}")
    suspend fun getProduct(@Path("barcode") barcode: String): ProductDetailsDto

    @POST("products/{barcode}/check-allergens")
    suspend fun checkAllergens(@Path("barcode") code: String): Response<Unit>

    @POST("products/{barcode}/save")
    suspend fun saveProduct(@Path("barcode") barcode: String): Response<Unit>

    @GET("products/saved")
    suspend fun getSavedProducts(): List<ProductDetailsDto>

    @POST("products/add")
    suspend fun createProduct(@Body request: CreateProductRequest): ProductDetailsDto

    companion object {
        fun create(): ProductApi {
            return RetrofitInstance.productApi
        }
    }
}