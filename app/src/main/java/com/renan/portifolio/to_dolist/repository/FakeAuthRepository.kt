package com.renan.portifolio.to_dolist.repository

import com.renan.portifolio.to_dolist.model.HomeResponse
import com.renan.portifolio.to_dolist.model.LoginResponse
import com.renan.portifolio.to_dolist.model.User
import com.renan.portifolio.to_dolist.network.AuthApi

class FakeAuthRepository : AuthRepository {
    override suspend fun login(email: String, password: String): LoginResponse {
        return LoginResponse(
            token = "mock_token",
            user = User(id = 1, name = "Mock User", email = "mock@example.com")
        )
    }

    override suspend fun home(email: String, password: String): HomeResponse {
        return HomeResponse(
            token = "mock_token",
            user = User(id = 1, name = "User", email = "mock@example.com")
        )
    }
}