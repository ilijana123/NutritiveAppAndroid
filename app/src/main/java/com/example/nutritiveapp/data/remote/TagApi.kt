package com.example.nutritiveapp.data.remote

import com.example.nutritiveapp.data.model.Tag
import retrofit2.http.GET

interface TagApi {
    @GET("tags")
    suspend fun getAllTags(): List<Tag>
}