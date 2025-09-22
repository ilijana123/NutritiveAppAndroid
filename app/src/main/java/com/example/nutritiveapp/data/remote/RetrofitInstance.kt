package com.example.nutritiveapp.data.remote

import android.content.Context
import android.util.Log
import com.example.nutritiveapp.App
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://nutritiveapp.click:8443/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val prefs = App.context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
            val token = prefs.getString("jwt_token", null)
            Log.d("Retrofit", "Using token: $token")
            val requestBuilder = chain.request().newBuilder()
            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            chain.proceed(requestBuilder.build())
        }
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userApi: UserApi by lazy { retrofit.create(UserApi::class.java) }
    val allergenApi: AllergenApi by lazy { retrofit.create(AllergenApi::class.java) }
    val productApi: ProductApi by lazy { retrofit.create(ProductApi::class.java) }
    val tagApi: TagApi by lazy { retrofit.create(TagApi::class.java) }
    val countryApi: CountryApi by lazy { retrofit.create(CountryApi::class.java) }
    val categoryApi: CategoryApi by lazy { retrofit.create(CategoryApi::class.java) }
    val nutrimentApi: NutrimentApi by lazy { retrofit.create(NutrimentApi::class.java) }
    val spoonacularApi: SpoonacularApi by lazy { retrofit.create(SpoonacularApi::class.java) }
    val deviceApi: DeviceApi by lazy { retrofit.create(DeviceApi::class.java) }
}
