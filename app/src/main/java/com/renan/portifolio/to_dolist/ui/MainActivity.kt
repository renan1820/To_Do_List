package com.renan.portifolio.to_dolist.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.renan.portifolio.to_dolist.ui.theme.ToDoListTheme
import com.renan.portifolio.to_dolist.ui.login.viewmodel.LoginViewModel
import com.renan.portifolio.to_dolist.ui.navigation.NavGraph
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    android.graphics.Color.TRANSPARENT,
                    android.graphics.Color.TRANSPARENT
                )
            )
            ToDoListTheme{
                val navController = rememberNavController()
                NavGraph(navController = navController)

            }
        }
    }
}
