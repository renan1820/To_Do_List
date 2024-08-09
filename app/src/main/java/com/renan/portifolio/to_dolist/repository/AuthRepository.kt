package com.renan.portifolio.to_dolist.repository

import com.renan.portifolio.to_dolist.model.HomeResponse
import com.renan.portifolio.to_dolist.model.LoginResponse
import com.renan.portifolio.to_dolist.network.AuthApi

interface AuthRepository {
    suspend fun login(email: String, password: String): LoginResponse
    suspend fun home(email: String, password: String): HomeResponse
}