package com.example.nutritiveapp.data.repository

import android.util.Log
import com.example.nutritiveapp.data.model.Tag
import com.example.nutritiveapp.data.remote.TagApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagRepository @Inject constructor(
    private val tagApi: TagApi
) {
    suspend fun getAllTags(): List<Tag> {
        return try {
            tagApi.getAllTags()
        } catch (e: Exception) {
            Log.e("TagRepository", "Failed to load tags", e)
            throw e
        }
    }
}