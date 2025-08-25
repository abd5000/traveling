package com.example.mytestnav.api

import AuthrazationToken
import com.example.mytestnav.home.refreshtoken.ViewModelToken
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory



private const val BASE_URL = "http://192.168.43.77:8000"



object Instance {
    val viewModel=ViewModelToken()
    private val myClinte=OkHttpClient.Builder().addInterceptor(AuthrazationToken(viewModel)).build()
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    val apiServes:Api by lazy {
        retrofit.create(Api::class.java)
    }
}




//private val myClint= OkHttpClient.Builder().addInterceptor(AuthIntercepter()).build()