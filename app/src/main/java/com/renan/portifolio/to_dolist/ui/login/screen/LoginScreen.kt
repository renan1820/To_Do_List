package com.renan.portifolio.to_dolist.ui.login.screen

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.renan.portifolio.to_dolist.ui.login.viewmodel.LoginViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.renan.portifolio.to_dolist.viewmodel.MockViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = koinViewModel()) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val loginResponse by loginViewModel.loginResponse.observeAsState()
    val error by loginViewModel.error.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                loginViewModel.login(email.value, password.value)
                navController.navigate("home")
            }
        ) {
            Text("Login")
        }

        loginResponse?.let {
            Text("Login successful! Token: ${it.token}")
        }

        error?.let {
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController(), loginViewModel = MockViewModel())
}
