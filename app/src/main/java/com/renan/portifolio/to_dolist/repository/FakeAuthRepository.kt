package com.renan.portifolio.to_dolist.repository

import com.renan.portifolio.to_dolist.model.HomeResponse
import com.renan.portifolio.to_dolist.model.LoginResponse
import com.renan.portifolio.to_dolist.model.User
import com.renan.portifolio.to_dolist.network.AuthApi
import com.renan.portifolio.to_dolist.util.Resource

class FakeAuthRepository : AuthRepository {
    override suspend fun login(email: String, password: String): Resource<LoginResponse> {
        val loginResponse = LoginResponse(
            token = "mock_token",
            user = User(id = 1, name = "Mock User", email = "mock@example.com")
        )
        return Resource.Success(loginResponse)
    }

    override suspend fun home(email: String, password: String): HomeResponse {
        return HomeResponse(
            token = "mock_token",
            user = User(id = 1, name = "User", email = "mock@example.com")
        )
    }
}