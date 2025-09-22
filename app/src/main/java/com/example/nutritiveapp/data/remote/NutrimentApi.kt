package com.example.nutritiveapp.data.remote

import com.example.nutritiveapp.data.model.Nutriment
import retrofit2.http.GET

interface NutrimentApi {
    @GET("nutriments")
    suspend fun getAllNutriments(): List<Nutriment>
}