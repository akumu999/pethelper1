package com.example.pethelper.Screens.Register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    controller: NavController
) {
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bisque2)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Регистрация")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Пароль") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.register(controller) },
            modifier = Modifier.fillMaxWidth().shadow(40.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4)
        ) {
            Text("Зарегистрироваться", color = Color.White, fontSize = 20.sp)
        }
    }
}