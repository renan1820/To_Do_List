package com.renan.portifolio.to_dolist.ui.login.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renan.portifolio.to_dolist.model.LoginResponse
import com.renan.portifolio.to_dolist.repository.LoginRepositoryImpl
import com.renan.portifolio.to_dolist.ui.login.state.LoginState
import com.renan.portifolio.to_dolist.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class LoginViewModel: ViewModel(), KoinComponent {

    var state by mutableStateOf(LoginState())

    private val loginRepository: LoginRepositoryImpl by inject()

    private val _loginResponse = MutableLiveData<LoginResponse>()
    open val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _error = MutableLiveData<String>()
    open val error: LiveData<String> = _error

    open fun login(email: String, password: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            delay(3000L)
            when(val result = loginRepository.requestLogin(email, password)){
                is Resource.Success ->{
                    state = state.copy(
                        isLoading = false,
                        user = result.data?.user,
                        error = null
                    )
                }
                is Resource.Error ->{
                    state = state.copy(
                        isLoading = false,
                        user = result.data?.user,
                        error = result.message
                    )
                }
            }
        }
    }
}