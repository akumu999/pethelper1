package com.example.pethelper.Navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.example.pethelper.Screens.*
import com.example.pethelper.Screens.Doctors.DoctorScreen
import com.example.pethelper.Screens.Login.LoginViewModel
import com.example.pethelper.Screens.Register.RegisterScreen
import com.example.pethelper.Screens.Register.RegisterViewModel


@Composable
fun SetNavController(controller: NavHostController, context : Context) {
    NavHost(navController = controller, startDestination = NavScreens.StartScreen.route) {
        composable(NavScreens.StartScreen.route){
            StartScreen(controller)
        }
        composable(NavScreens.LoginScreen.route){
            val loginViewModel = viewModel<LoginViewModel>()
            LoginScreen(viewModel = loginViewModel, controller, context)
        }
        composable(NavScreens.DoctorScreen.route){
            DoctorScreen(controller)
        }
        composable(NavScreens.RegisterScreen.route){
            val authViewModel = viewModel<RegisterViewModel>()
            RegisterScreen(viewModel = authViewModel, controller)
        }
        composable(NavScreens.CatalogScreen.route){
            CatalogScreen(controller)
        }
        composable(NavScreens.PetsScreen.route){
            PetsScreen(controller)
        }
        composable(NavScreens.ProfileScreen.route){
            ProfileScreen(controller)
        }
    }
}