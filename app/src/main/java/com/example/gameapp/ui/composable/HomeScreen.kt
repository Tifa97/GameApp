package com.example.gameapp.ui.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gameapp.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<HomeViewModel>()
    val genres by viewModel.savedGenres.collectAsStateWithLifecycle()

    if (genres.isEmpty()) {
        Text(text = "There are no saved genres")
    } else {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(genres.size, itemContent = {
                Text(text = genres[it].name)
            })
        }
    }
}