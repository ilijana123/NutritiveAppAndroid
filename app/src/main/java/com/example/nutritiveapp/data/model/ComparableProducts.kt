package com.example.nutritiveapp.data.model

data class ComparableProducts(
    val calories: List<ComparableProduct>,
    val likes: List<ComparableProduct>,
    val price: List<ComparableProduct>,
    val protein: List<ComparableProduct>,
    val spoonacularScore: List<ComparableProduct>,
    val sugar: List<ComparableProduct>
)