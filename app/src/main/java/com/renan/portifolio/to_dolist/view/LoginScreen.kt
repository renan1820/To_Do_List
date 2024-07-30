package com.renan.portifolio.to_dolist.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.renan.portifolio.to_dolist.viewmodel.LoginViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.mutableStateOf
import org.koin.androidx.compose.getViewModel


@Composable
fun LoginScreen (loginViewModel: LoginViewModel = getViewModel()){
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val loginResponse = remember { mutableStateOf("") }
}