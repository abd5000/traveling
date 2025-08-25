package com.example.myadmin.home.edittrips.editeordelettrips

import com.example.myadmin.home.showtrips.availabletrips.Data

interface TripListener {
    fun onClickItem(trip:Data)
}