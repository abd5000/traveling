package com.example.myadmin.home.edittrips.editeordelettrips

data class AvailableTripsResponse(
    val `data`: List<Data?>?,
    val message: String?,
    val success: Boolean?
)