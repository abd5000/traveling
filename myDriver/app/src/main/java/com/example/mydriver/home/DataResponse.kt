package com.example.mydriver.home

data class DataResponse(
    val `data`: List<Data?>?,
    val message: String?,
    val success: Boolean?
)