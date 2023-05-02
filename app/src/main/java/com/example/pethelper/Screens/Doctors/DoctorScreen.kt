package com.example.pethelper.Screens.Doctors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.Screens.DoctorList
import com.example.pethelper.ui.theme.Bisque2

@Composable
fun DoctorScreen(controller: NavController) {
    Column(modifier = Modifier.background(Bisque2).padding(16.dp)){ DoctorList() } }