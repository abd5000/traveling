package com.example.myadmin.usersandbuss.users.banned

import com.example.myadmin.usersandbuss.users.banned.custoersBlocked.Data

interface CancleBlockedListener {
    fun onCancelBlocked(customer:Data, position:Int)

}