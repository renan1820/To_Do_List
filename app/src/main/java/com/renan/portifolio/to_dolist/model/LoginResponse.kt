package com.renan.portifolio.to_dolist.model

data class LoginResponse (
    val token: String,
    val user: User)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val createdAt: String
    )