package com.example.mytestnav.login.loginInformation

data class Result(
    val activeUser: Boolean?,
    val confirmpassword: String?,
    val createdAt: String?,
    val cusemail: String?,
    val fullname: String?,
    val id: Int?,
    val password: String?,
    val updatedAt: String?,
    val verificationCode: Any?
)