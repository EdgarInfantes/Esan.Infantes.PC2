package com.example.esaninfantespc2.navigation

//Para la navegacion entre pantallas
sealed class Screens(val route: String) {

    //Pantalla del Login
    object Login : Screens("login")

    //Pantalla del Home
    object Home : Screens("home")

    //Pantalla para el registro
    object Register : Screens("register")

}