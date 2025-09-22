package com.example.nutritiveapp.data.model.response

data class AuthResponse(
    val id: Long,
    val email: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val token: String
)
