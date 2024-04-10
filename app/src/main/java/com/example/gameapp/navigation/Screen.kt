package com.example.gameapp.navigation

sealed class Screen(val route: String) {
    data object GenreSelection: Screen(route = "genre_selection_screen")
    data object Home: Screen(route = "home_screen")
    data object GameDetails: Screen(route = "game_details_screen")
}