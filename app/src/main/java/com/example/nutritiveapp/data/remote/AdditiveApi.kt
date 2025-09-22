package com.example.nutritiveapp.data.remote

import com.example.nutritiveapp.data.model.Additive
import retrofit2.http.GET

interface AdditiveApi {
    @GET("allergens")
    suspend fun getAllAdditives(): List<Additive>
}