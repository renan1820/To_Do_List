package com.renan.portifolio.to_dolist.di

import com.renan.portifolio.to_dolist.network.AuthApi
import com.renan.portifolio.to_dolist.repository.AuthRepository
import com.renan.portifolio.to_dolist.repository.FakeAuthRepository
import com.renan.portifolio.to_dolist.ui.home.viewmodel.HomeViewModel
import com.renan.portifolio.to_dolist.ui.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(AuthApi::class.java) }
    
    viewModel{ LoginViewModel(get()) }

    single<AuthRepository> { FakeAuthRepository() }

    viewModel{ HomeViewModel(get())}
}