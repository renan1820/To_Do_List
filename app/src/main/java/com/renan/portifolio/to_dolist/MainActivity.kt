package com.renan.portifolio.to_dolist

import android.app.Application
import com.renan.portifolio.to_dolist.di.appModule
import org.koin.android.ext.koin.androidContext

import org.koin.core.context.startKoin

class MainActivity : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }
    }
}