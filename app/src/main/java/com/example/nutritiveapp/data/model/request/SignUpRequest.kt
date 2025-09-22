package com.example.nutritiveapp.data.model.request

data class SignUpRequest(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val password: String
)
