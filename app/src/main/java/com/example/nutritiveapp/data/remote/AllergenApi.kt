package com.example.nutritiveapp.data.remote

import com.example.nutritiveapp.data.dto.AllergenDTO
import com.example.nutritiveapp.data.model.Allergen
import com.example.nutritiveapp.data.model.request.AssignAllergensRequest
import retrofit2.Call
import retrofit2.http.*

interface AllergenApi {
    @GET("allergens")
    suspend fun getAllAllergens(): List<AllergenDTO>

    @GET("allergens/search")
    suspend fun searchAllergensByName(@Query("name") name: String): List<AllergenDTO>

    @PUT("users/{userId}/allergens")
    fun assignAllergens(
        @Path("userId") userId: Long,
        @Body body: AssignAllergensRequest
    ): Call<Unit>

    @GET("users/{userId}/allergens")
    fun getUserAllergens(
        @Path("userId") userId: Long
    ): Call<List<Allergen>>
}
