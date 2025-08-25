package com.example.mydriver.home

import java.io.Serializable

data class Data(
    val arrivalTime: String?,
    val company: String?,
    val destination: String?,
    val duration: String?,
    val fullname: String?,
    val id: Int?,
    val idDriver: Int?,
    val numberbus: Int?,
    val numberdisksisFalse: List<Int?>?,
    val price: Int?,
    val starting: String?,
    val timeTrip: String?,
    val tripDate: String?,
    val tripTime: String?,
    val typebus: String?
):Serializable