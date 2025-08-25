package com.example.myadmin.home.showtrips.reserve.completereserve

import java.io.Serializable

data class Data(
    val arrivalTime: String?,
    val company: String?,
    val destination: String?,
    val duration: String?,
    val id: Int?,
    val numberbus: Int?,
    val numberdisksisFalse: List<Int?>?,
    val price: Int?,
    val starting: String?,
    val tripDate: String?,
    val tripTime: String?,
    val typebus: String?
):Serializable