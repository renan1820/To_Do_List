package com.renan.portifolio.to_dolist.repository

import com.renan.portifolio.to_dolist.model.LoginResponse
import com.renan.portifolio.to_dolist.network.AuthApi

interface AuthRepository {
    suspend fun login(email: String, password: String): LoginResponse
}