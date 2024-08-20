package com.renan.portifolio.to_dolist.ui.login.state

import com.renan.portifolio.to_dolist.model.User

data class LoginState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: User? = null
)
