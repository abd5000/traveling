package com.example.mytestnav.mybook.paid

data class Data(
    val arrivalTime: String?,
    val company: String?,
    val destination: String?,
    val duration: String?,
    val id: Int?,
    val numberbus: Int?,
    val numberdisksispaid: List<Int?>?,
    val numberdisksisnotpaid:List<Int?>?,
    val price: Int?,
    val starting: String?,
    val tripDate: String?,
    val tripTime: String?,
    val typebus: String?
)