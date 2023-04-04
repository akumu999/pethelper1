package com.example.pethelper.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pethelper.*


@Composable
fun SetNavController(controller: NavHostController) {
    NavHost(navController = controller, startDestination = NavScreens.StartScreen.route) {
        composable(NavScreens.StartScreen.route){
            StartScreen(controller)
        }
        composable(NavScreens.LoginScreen.route){
            LoginScreen(controller, onLogin = {
                username, password ->
            })
        }

        composable(NavScreens.RegisterScreen.route){
            val controller = rememberNavController()
            val authViewModel = viewModel<AuthViewModel>()
            RegisterScreen(viewModel = authViewModel, navController = controller)
        }

        composable(NavScreens.MainScreen.route){
            MainScreen(controller)
        }
        composable(NavScreens.OrderScreen.route){
            OrderScreen(controller)
        }
        composable(NavScreens.CatalogScreen.route){
            CatalogScreen(controller)
        }
        composable(NavScreens.PetsScreen.route){
            PetsScreen(controller)
        }
        composable(NavScreens.ProfileScreen.route){
            ProfileScreen()
        }
    }
}