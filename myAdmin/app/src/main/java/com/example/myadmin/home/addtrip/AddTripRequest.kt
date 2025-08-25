package com.example.myadmin.home.addtrip

data class AddTripRequest(
    val busid: Int?,
    val destination: Int?,
    val starting: Int?,
    val tripDate: String?,
    val tripTime: String?,
    val isRecurring:Boolean?
)