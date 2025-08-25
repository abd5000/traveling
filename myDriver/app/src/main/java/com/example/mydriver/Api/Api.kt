package com.example.mydriver.Api

import com.example.mydriver.Login.LoginInfo
import com.example.mydriver.Login.LoginResponse
import com.example.mydriver.home.DataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {
    @POST("logindriver")
    suspend fun login(@Body admin: LoginInfo): Response<LoginResponse>
    @GET("getinfodriver")
    suspend fun getInfo(@Header("Authorization") token: String):Response<DataResponse>
}