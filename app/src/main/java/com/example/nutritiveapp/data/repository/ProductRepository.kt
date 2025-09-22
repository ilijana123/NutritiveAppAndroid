package com.example.nutritiveapp.data.repository

import com.example.nutritiveapp.data.model.Product
import com.example.nutritiveapp.data.model.ProductPage
import com.example.nutritiveapp.data.model.request.ProductFilterRequest
import com.example.nutritiveapp.data.model.response.PageResponse
import com.example.nutritiveapp.data.remote.ProductApi
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class ProductRepository @Inject constructor(
//    private val productApi: ProductApi
//) {
//    suspend fun getFilteredProducts(filterRequest: ProductFilterRequest): ProductPage {
//        return productApi.getFilteredProducts(filterRequest)
//    }
//}