package com.example.esaninfantespc2.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.esaninfantespc2.presentation.home.HistorialScreen
import com.example.esaninfantespc2.presentation.home.HomeScreen
import com.example.esaninfantespc2.presentation.home.ProfileScreen
import com.example.esaninfantespc2.viewmodel.UserSessionViewModel

@Composable
fun HomeNavigation(
    mainNavController: NavHostController,
    userSessionViewModel: UserSessionViewModel
) {
    val bottomNavController = rememberNavController()

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Historial,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            BottomBar(navController = bottomNavController, items = items)
        }
    ) { padding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(navController = mainNavController, userSessionViewModel = userSessionViewModel)
            }

            composable(BottomNavItem.Historial.route) {
                HistorialScreen(navController = mainNavController, userSessionViewModel = userSessionViewModel)
            }

            composable(BottomNavItem.Profile.route) {
                ProfileScreen(navController = mainNavController, userSessionViewModel = userSessionViewModel)
            }


        }
    }
}
