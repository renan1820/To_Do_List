package com.renan.portifolio.to_dolist.di

import com.renan.portifolio.to_dolist.network.AuthApi
import com.renan.portifolio.to_dolist.network.data.RetrofitClient
import com.renan.portifolio.to_dolist.repository.AuthRepository
import com.renan.portifolio.to_dolist.repository.FakeAuthRepository
import com.renan.portifolio.to_dolist.repository.LoginRepositoryImpl
import com.renan.portifolio.to_dolist.ui.home.viewmodel.HomeViewModel
import com.renan.portifolio.to_dolist.ui.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitClient().create().create(AuthApi::class.java) }
    single { LoginRepositoryImpl() }

    viewModel{ LoginViewModel() }

    single<AuthRepository> { FakeAuthRepository() }

    viewModel{ HomeViewModel(get())}
}