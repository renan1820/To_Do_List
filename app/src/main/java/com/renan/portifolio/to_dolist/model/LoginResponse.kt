package com.renan.portifolio.to_dolist.model

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: User)

data class User(
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String = "",
    var createdAt: String = ""
    )