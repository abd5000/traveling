package com.example.myadmin.usersandbuss.addDriver

data class AddDriverRequest(
    val confirmpassword: String?,
    val email: String?,
    val fullname: String?,
    val password: String?,
    val phone: String?
)