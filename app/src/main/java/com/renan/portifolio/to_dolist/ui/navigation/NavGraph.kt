package com.renan.portifolio.to_dolist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.renan.portifolio.to_dolist.ui.home.screen.HomeScreen
import com.renan.portifolio.to_dolist.ui.login.screen.LoginScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("home") {
            HomeScreen()
        }
    }
}