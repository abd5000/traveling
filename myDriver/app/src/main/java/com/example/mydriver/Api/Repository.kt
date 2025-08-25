package com.example.mydriver.Api

import com.example.mydriver.Login.LoginInfo
import com.example.mydriver.Login.LoginResponse
import com.example.mydriver.home.DataResponse
import com.google.gson.Gson

class Repository {
    suspend fun login(email:String,password:String): LoginResponse?{
        val result=Instance.api.login(LoginInfo(email, password))
        //
        return try {
            result.body()!!
        } catch (error: Throwable) {
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), LoginResponse::class.java)
            errorResponse
        }
    }
    suspend fun getInfo(token:String):DataResponse?{
        val result=Instance.api.getInfo("Bearer $token")
        //
        return try {
            result.body()!!
        } catch (error: Throwable) {
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), DataResponse::class.java)
            errorResponse
        }
    }
}