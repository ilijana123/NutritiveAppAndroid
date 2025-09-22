package com.example.nutritiveapp.data.remote

import com.example.nutritiveapp.data.model.Country
import retrofit2.http.GET

interface CountryApi {
    @GET("countries")
    suspend fun getAllCountries(): List<Country>
}