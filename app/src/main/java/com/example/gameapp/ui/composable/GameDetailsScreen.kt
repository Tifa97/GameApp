package com.example.gameapp.ui.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun GameDetailsScreen(
    gameName: String?,
    navController: NavController, 
    modifier: Modifier = Modifier
) {
    gameName?.let {
        Text(text = gameName)
    }
}