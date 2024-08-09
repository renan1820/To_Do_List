package com.renan.portifolio.to_dolist.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renan.portifolio.to_dolist.model.HomeResponse
import com.renan.portifolio.to_dolist.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class HomeViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _homeResponse = MutableLiveData<HomeResponse>()
    open val homeResponse: LiveData<HomeResponse> = _homeResponse

    private val _error = MutableLiveData<String>()
    open val error: LiveData<String> = _error

    open fun home(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.home(email, password)
                _homeResponse.value = response
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}