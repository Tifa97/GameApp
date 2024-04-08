package com.example.gameapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gameapp.ui.composable.HomeScreen
import com.example.gameapp.ui.composable.OnboardingScreen

@Composable
fun GameAppNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.Onboarding.route) {
            OnboardingScreen()
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
    }
}