package com.example.nutritiveapp.data.model

import com.example.nutritiveapp.data.dto.ProductDetailsDto

data class ProductPage(
    val content: List<ProductDetailsDto>,
    val totalElements: Long,
    val totalPages: Int,
    val size: Int,
    val number: Int,
    val first: Boolean,
    val last: Boolean
)