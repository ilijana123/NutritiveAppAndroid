package com.example.nutritiveapp.data.repository

import com.example.nutritiveapp.data.model.Country
import com.example.nutritiveapp.data.remote.CountryApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepository @Inject constructor(
    private val countryApi: CountryApi
) {
    suspend fun getAllCountries(): List<Country> {
        return try {
            countryApi.getAllCountries()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
