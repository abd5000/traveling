package com.example.myadmin.usersandbuss.addbuss

data class AddBusRequest(
    val number: Int?,
    val place: String?,
    val type: String?,
  val driverId:Int?
)