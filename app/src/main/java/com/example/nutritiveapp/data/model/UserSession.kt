package com.example.nutritiveapp.data.model

data class UserSession(
    val id: Long? = null,
    val email: String? = null,
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val token: String? = null
)