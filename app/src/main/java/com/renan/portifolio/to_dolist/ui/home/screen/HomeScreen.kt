package com.renan.portifolio.to_dolist.ui.home.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.renan.portifolio.to_dolist.ui.home.viewmodel.HomeViewModel
import com.renan.portifolio.to_dolist.viewmodel.MockViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = koinViewModel()) {
    Text(text = "Welcome to Home Screen!")
}

@Preview
@Composable
fun HomePreview(){
//    HomeScreen(homeViewModel = MockViewModel())
}