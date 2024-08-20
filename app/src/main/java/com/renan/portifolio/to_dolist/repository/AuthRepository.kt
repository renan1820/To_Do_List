package com.renan.portifolio.to_dolist.repository

import com.renan.portifolio.to_dolist.model.HomeResponse
import com.renan.portifolio.to_dolist.model.LoginResponse
import com.renan.portifolio.to_dolist.network.AuthApi
import com.renan.portifolio.to_dolist.util.Resource

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<LoginResponse>
    suspend fun home(email: String, password: String): HomeResponse
}