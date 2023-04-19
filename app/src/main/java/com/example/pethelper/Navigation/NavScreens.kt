package com.example.pethelper.Navigation

sealed class NavScreens (val route: String){

    object StartScreen : NavScreens("StartScreen")
    object LoginScreen : NavScreens("LoginScreen")
    object RegisterScreen : NavScreens("RegisterScreen")
    object DoctorScreen : NavScreens("DoctorScreen")
    object OrderScreen : NavScreens("OrderScreen")
    object CatalogScreen : NavScreens("CatalogScreen")
    object PetsScreen : NavScreens("PetsScreen")
    object ProfileScreen : NavScreens("ProfileScreen")
}