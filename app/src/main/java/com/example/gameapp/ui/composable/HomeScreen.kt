package com.example.gameapp.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gameapp.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<HomeViewModel>()
    val genres by viewModel.savedGenres.collectAsStateWithLifecycle()
    val selectedGenre by viewModel.selectedGenre.collectAsStateWithLifecycle()

    val games by remember { viewModel.games }
    val isLoading by remember { viewModel.isLoading }
    val loadError by remember { viewModel.loadError }

    Column {
        TopBar()
        if (genres.isEmpty()) {
            // Handle this in a way that you can't finish Onboarding without selecting a genre
            Text(text = "There are no saved genres")
        } else {
            if (selectedGenre.isEmpty()) viewModel.setSelectedGenre(genres[0].name)
            Column(modifier = modifier.fillMaxSize()) {
                LazyRow {
                    items(genres.size, itemContent = {
                        Box(
                            modifier = modifier.clickable {
                                viewModel.setSelectedGenre(genres[it].name)
                            }
                        ) {
                            Text(
                                text = genres[it].name,
                                modifier = modifier.padding(16.dp)
                            )
                        }
                    })
                }
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    LazyColumn(modifier = modifier.fillMaxSize()) {
                        items(games.size, itemContent = {
                            games[it].name?.let { name ->
                                Text(text = name)
                            }
                        })
                    }
                }
            }
        }
    }
}