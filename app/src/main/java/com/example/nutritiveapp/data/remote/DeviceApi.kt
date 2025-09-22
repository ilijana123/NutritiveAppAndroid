package com.example.nutritiveapp.data.remote

import com.example.nutritiveapp.data.model.request.DeviceTokenRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DeviceApi {
    @POST("devices/register")
    fun registerDevice(@Body body: DeviceTokenRequest): Call<Unit>
}
