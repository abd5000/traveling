package com.example.myadmin.usersandbuss.users.toReview

interface BlockedUserListener {
    fun onBlocked(customer:Data, position:Int)
    fun onDetails(customer: Data)

}