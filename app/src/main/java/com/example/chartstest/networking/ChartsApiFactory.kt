package com.example.chartstest.networking

import com.example.chartstest.utils.Constants.Companion.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ChartsApiFactory {
    /**
     * Moshi for data serialization
     */
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val apiService: ChartsApiService = retrofit().create(ChartsApiService::class.java)

    /**
     * Retrofit setup
     */
    private fun retrofit(log: Boolean = true): Retrofit = Retrofit.Builder().apply {
        client(OkHttpClient.Builder().addInterceptor(MockInterceptor()).build())
        baseUrl(BASE_URL)
        addConverterFactory(MoshiConverterFactory.create(moshi))
    }.build()


}