package com.example.myadmin.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
private const val BASE_URL = "http://192.168.43.77:8000"
object
Instance {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }
    val api:Api by lazy {
        retrofit.create(Api::class.java)
    }
}