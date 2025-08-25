package com.example.myadmin.home.tracking

import com.example.myadmin.home.showtrips.availabletrips.Data

interface TrackingTripListener {
    fun onTrackingTrip(trip:Data)
}