package com.example.nutritiveapp.di

import com.example.nutritiveapp.data.remote.AllergenApi
import com.example.nutritiveapp.data.remote.CategoryApi
import com.example.nutritiveapp.data.remote.CountryApi
import com.example.nutritiveapp.data.remote.ProductApi
import com.example.nutritiveapp.data.remote.TagApi
import com.example.nutritiveapp.data.remote.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAllergenApi(): AllergenApi {
        return RetrofitInstance.allergenApi
    }

    @Provides
    @Singleton
    fun provideCategoryApi(): CategoryApi {
        return RetrofitInstance.categoryApi
    }

    @Provides
    @Singleton
    fun provideTagApi(): TagApi {
        return RetrofitInstance.tagApi
    }

    @Provides
    @Singleton
    fun provideCountryApi(): CountryApi {
        return RetrofitInstance.countryApi
    }

    @Provides
    @Singleton
    fun provideProductApi(): ProductApi {
        return RetrofitInstance.productApi
    }
}