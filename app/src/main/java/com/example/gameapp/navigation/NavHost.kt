package com.example.gameapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gameapp.ui.composable.GameDetailsScreen
import com.example.gameapp.ui.composable.GenreSelectionScreen
import com.example.gameapp.ui.composable.HomeScreen

@Composable
fun GameAppNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.GenreSelection.route) {
            GenreSelectionScreen(navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = "${Screen.GameDetails.route}/{gameId}",
            arguments = listOf(
                navArgument("gameId") {
                    type = NavType.StringType
                }
            )
        ) {
            val gameId = remember {
                it.arguments?.getString("gameId")
            }
            GameDetailsScreen(gameId = gameId, navController = navController)
        }
    }
}