package com.example.pethelper.Screens

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TopNav(){
    TopAppBar(
        title = { Text(text = "PETHELPER", textAlign = TextAlign.Center) }
    )
}