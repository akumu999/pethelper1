package com.example.pethelper.Screens

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens

@Composable
fun BottomNav(controller: NavController){
    BottomNavigation {
        BottomNavigationItem(
            selected = true,
            onClick = { controller.navigate(NavScreens.DoctorScreen.route)},
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Записаться на прием") },
            label = { Text("Записаться на прием") }
        )

        BottomNavigationItem(
            selected = false,
            onClick = { controller.navigate(NavScreens.CatalogScreen.route) },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Каталог товаров") },
            label = { Text("Каталог товаров") }
        )

        BottomNavigationItem(
            selected = false,
            onClick = { controller.navigate(NavScreens.PetsScreen.route) },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Мои питомцы") },
            label = { Text("Мои питомцы") }
        )

        BottomNavigationItem(
            selected = false,
            onClick = { controller.navigate(NavScreens.ProfileScreen.route) },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Профиль") },
            label = { Text("Профиль") }
        )
    }
}