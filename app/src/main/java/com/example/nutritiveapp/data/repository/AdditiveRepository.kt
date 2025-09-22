package com.example.nutritiveapp.data.repository

import com.example.nutritiveapp.data.model.Additive
import com.example.nutritiveapp.data.remote.AdditiveApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdditiveRepository @Inject constructor(
    private val additiveApi: AdditiveApi
) {
    suspend fun getAllAdditives(): List<Additive> {
        return try {
            additiveApi.getAllAdditives()
        } catch (e: Exception) {
            emptyList()
        }
    }
}