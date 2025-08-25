package com.example.myadmin.usersandbuss.deletebus.editeBus

import com.example.myadmin.usersandbuss.deletebus.allbuss.DataX

data class EditeBusResponse(
    val `data`: DataX?,
    val message: String?,
    val success: Boolean?
)