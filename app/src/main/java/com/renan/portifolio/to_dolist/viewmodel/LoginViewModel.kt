package com.renan.portifolio.to_dolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renan.portifolio.to_dolist.model.LoginResponse
import com.renan.portifolio.to_dolist.repository.AuthRepository
import kotlinx.coroutines.launch

open class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse>()
    open val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _error = MutableLiveData<String>()
    open val error: LiveData<String> = _error

    open fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                _loginResponse.value = response
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}