package com.example.nutritiveapp.data.model.request

data class UpdateUserRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String
)