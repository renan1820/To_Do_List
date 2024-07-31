package com.renan.portifolio.to_dolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.renan.portifolio.to_dolist.model.LoginResponse
import com.renan.portifolio.to_dolist.model.User
import com.renan.portifolio.to_dolist.repository.FakeAuthRepository

class MockLoginViewModel : LoginViewModel(FakeAuthRepository()) {
    override val loginResponse: LiveData<LoginResponse> = MutableLiveData(
        LoginResponse(
            token = "mock_token",
            user = User(id = 1, name = "Mock User", email = "mock@example.com")
        )
    )
    override val error: LiveData<String> = MutableLiveData("Mock error message")
}