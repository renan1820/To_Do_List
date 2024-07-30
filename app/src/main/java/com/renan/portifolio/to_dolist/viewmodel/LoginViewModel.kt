package com.renan.portifolio.to_dolist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renan.portifolio.to_dolist.model.LoginResponse
import com.renan.portifolio.to_dolist.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    val loginResponse = MutableLiveData<LoginResponse>()
    val error = MutableLiveData<String>()

    fun login(email: String, password: String){
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                loginResponse.value = response
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }
}