package com.renan.portifolio.to_dolist.repository

import com.renan.portifolio.to_dolist.model.LoginResponse
import com.renan.portifolio.to_dolist.network.AuthApi

class AuthRepository(private val authApi: AuthApi) {
    suspend fun login(email: String, password: String): LoginResponse {
        return authApi.login(email, password)
    }

}