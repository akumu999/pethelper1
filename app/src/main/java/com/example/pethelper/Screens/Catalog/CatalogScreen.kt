package com.example.pethelper.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.Screens.Catalog.CatalogList
import com.example.pethelper.ui.theme.Bisque2

@Composable
fun CatalogScreen(controller: NavController) {
    Column(modifier = Modifier.background(Bisque2).padding(16.dp)){ CatalogList() } }