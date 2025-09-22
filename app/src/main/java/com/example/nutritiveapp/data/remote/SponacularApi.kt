package com.example.nutritiveapp.data.remote

import com.example.nutritiveapp.data.model.response.ComparableResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularApi {
    @GET("food/products/upc/{upc}/comparable")
    suspend fun getComparableProducts(
        @Path("upc") upc: String,
        @Query("apiKey") apiKey: String
    ): ComparableResponse
}
