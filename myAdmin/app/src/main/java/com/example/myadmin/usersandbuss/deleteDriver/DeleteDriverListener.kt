package com.example.myadmin.usersandbuss.deleteDriver

import java.text.FieldPosition

interface DeleteDriverListener {
    fun onReserveDriver(driver:Data,position:Int)
    fun onCallPhone(driver: Data)
}