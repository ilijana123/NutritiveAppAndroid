package com.example.nutritiveapp.data.remote

import com.example.nutritiveapp.data.model.response.AuthResponse
import com.example.nutritiveapp.data.model.request.LoginRequest
import com.example.nutritiveapp.data.model.request.SignUpRequest
import com.example.nutritiveapp.data.model.request.UpdateUserRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @POST("api/users/create")
    fun createUser(@Body request: SignUpRequest): Call<AuthResponse>

    @POST("api/users/login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>

    @PUT("api/users/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: Long,
        @Body request: UpdateUserRequest,
        @Header("Authorization") token: String
    ): AuthResponse
}