package com.example.myadmin.home.showtrips.availabletrips

interface AvailableTripListener {
    fun onClickItem(trip:Data)
    fun onReserveItem(trip:Data)
}