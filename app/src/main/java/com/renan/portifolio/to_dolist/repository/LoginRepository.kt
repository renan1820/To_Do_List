package com.renan.portifolio.to_dolist.repository

import com.renan.portifolio.to_dolist.model.LoginResponse
import com.renan.portifolio.to_dolist.network.AuthApi
import com.renan.portifolio.to_dolist.util.Resource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface LoginRepository {
    suspend fun requestLogin(email: String, password: String): Resource<LoginResponse?>
}

class LoginRepositoryImpl: LoginRepository, KoinComponent{

    private val request: AuthApi by inject()

    override suspend fun requestLogin(email: String, password: String): Resource<LoginResponse?> {
        return try {
            Resource.Success(request.login(email,password))
        }catch (e: Exception){
            Resource.Error(message = "Fail: ${e.message}")
        }
    }

}