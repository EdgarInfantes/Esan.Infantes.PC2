package com.example.esaninfantespc2.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.esaninfantespc2.presentation.home.HomeScreen
import com.example.esaninfantespc2.presentation.home.ProfileScreen
import com.example.esaninfantespc2.viewmodel.UserSessionViewModel

@Composable
fun HomeNavigation(mainNavController: NavHostController, userSessionViewModel: UserSessionViewModel) {
    val bottomNavController = rememberNavController()
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = { BottomBar(navController = bottomNavController, items = items) }
    ) { padding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(mainNavController, userSessionViewModel)
            }
            composable(BottomNavItem.Profile.route) { ProfileScreen(mainNavController, userSessionViewModel) }
            // Agregar más pantallas
        }
    }
}