package com.example.mytestnav.login.confirmtheemail.confirmsignup

data class User(
    val fullname:String?,
    val activeUser: Boolean?,
    val confirmpassword: String?,
    val createdAt: String?,
    val cusemail: String?,
    val id: Int?,
    val password: String?,
    val updatedAt: String?,
    val verificationCode: Any?
)