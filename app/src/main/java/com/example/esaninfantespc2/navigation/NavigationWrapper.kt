package com.example.esaninfantespc2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.esaninfantespc2.presentation.auth.LoginScreen
import com.example.esaninfantespc2.viewmodel.UserSessionViewModel

@Composable
fun NavigationWrapper(
    startDestination: String,
    userSessionViewModel: UserSessionViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(navController, userSessionViewModel)
        }

        composable("home") {
            HomeNavigation(navController, userSessionViewModel)
        }
        //composable("register") {
        //    RegisterScreen(navController)
        //}
    }
}