package com.renan.portifolio.to_dolist.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.renan.portifolio.to_dolist.data.SecurityPreferences
import com.renan.portifolio.to_dolist.data.daos.IPreferencesDataSource
import com.renan.portifolio.to_dolist.data.daos.PreferencesDataSource
import com.renan.portifolio.to_dolist.network.AuthApi
import com.renan.portifolio.to_dolist.network.data.RetrofitClient
import com.renan.portifolio.to_dolist.repository.AuthRepository
import com.renan.portifolio.to_dolist.repository.FakeAuthRepository
import com.renan.portifolio.to_dolist.repository.LoginRepositoryImpl
import com.renan.portifolio.to_dolist.ui.home.viewmodel.HomeViewModel
import com.renan.portifolio.to_dolist.ui.login.viewmodel.LoginViewModel
import com.renan.portifolio.to_dolist.util.tryCatchErrorReturn
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitClient().create().create(AuthApi::class.java) }
    single { LoginRepositoryImpl() }

    factory<IPreferencesDataSource> {
        tryCatchErrorReturn(tag = SecurityPreferences.TAG,
            runTry = {
                SecurityPreferences(
                    get(),
                    androidContext()
                )
            }, runCatch = {
                PreferencesDataSource(
                    androidContext().getSharedPreferences(
                        PreferencesDataSource.PROJECT_PREF_FILE_NAME,
                        Context.MODE_PRIVATE
                    )
                )
            })
    }

    single {
        PreferencesDataSource(
            androidContext().getSharedPreferences(
                PreferencesDataSource.PROJECT_PREF_FILE_NAME,
                Context.MODE_PRIVATE
            )
        )
    }

    single { FirebaseAnalytics.getInstance(androidContext()) }

    viewModel{ LoginViewModel() }

    single<AuthRepository> { FakeAuthRepository() }

    viewModel{ HomeViewModel(get())}
}