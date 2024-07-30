package com.renan.portifolio.to_dolist

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.renan.portifolio.to_dolist.view.BakingScreen
import com.renan.portifolio.to_dolist.view.ui.theme.ToDoListTheme
import org.koin.android.ext.koin.androidContext

import org.koin.core.context.startKoin

class MainActivity : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainActivity)

        }
    }
}