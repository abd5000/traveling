package com.example.myadmin.usersandbuss.deletebus

import com.example.myadmin.usersandbuss.deletebus.allbuss.Data
import java.text.FieldPosition

interface DeleteBusListener {
    fun onReserveBus(bus:com.example.myadmin.usersandbuss.deletebus.allbuss.Data,position:Int)
    fun onEditeBus(bus: Data)
}