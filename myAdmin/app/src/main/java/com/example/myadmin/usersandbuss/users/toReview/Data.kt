package com.example.myadmin.usersandbuss.users.toReview

import java.io.Serializable

data class Data(
    val fullname: String?,
    val id: Int?,
    val trips: List<Trip?>?
):Serializable