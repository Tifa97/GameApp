package com.example.gameapp.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.gameapp.R
import com.example.gameapp.db.entity.GenreItem
import com.example.gameapp.navigation.Screen
import com.example.gameapp.viewmodel.GenreSelectionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GenreSelectionScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<GenreSelectionViewModel>()

    val savedGenres = viewModel.savedGenres.collectAsStateWithLifecycle()
    val isOnboardingDone = viewModel.isOnboardingDone.collectAsStateWithLifecycle()

    val genres by remember { viewModel.genres }
    val isLoading by remember { viewModel.isLoading }
    val loadError by remember { viewModel.loadError }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxSize()) {
            TopBar()
            LoadError(error = loadError)
            if (isLoading) {
                Box (modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        color = Color.Black,
                        modifier = modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(modifier = modifier.fillMaxHeight(0.9f)) {
                    items(genres.size, itemContent = { index ->
                        genres[index].id?.let { id ->
                            genres[index].name?.let {name ->
                                val genre = GenreItem(
                                    genreId = id,
                                    name = name,
                                    isSelected = savedGenres.value.any { it.genreId == id }
                                )
                                GenreEntry(
                                    genre = genre,
                                    viewModel = viewModel
                                )
                                if (index != genres.size) {
                                    Spacer(
                                        modifier = modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(Color.White)
                                    )
                                }
                            }
                        }
                    })
                }
                Box(modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()) {
                    Button(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = {
                            if (!isOnboardingDone.value) {
                                viewModel.saveOnboardingFinished()
                            }
                            viewModel.saveGenreEdits()
                            navController.navigate(Screen.Home.route)
                        }
                    ) {
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    }
}

@Composable
fun GenreEntry(
    genre: GenreItem,
    viewModel: GenreSelectionViewModel,
    modifier: Modifier = Modifier
) {
    // rememberSaveable makes sure that switch state survives LazyColumn scroll
    val isChecked = rememberSaveable { mutableStateOf(genre.isSelected) }

    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = genre.name)
        Switch(
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it
                genre.isSelected = isChecked.value
                viewModel.editSelectedGenreList(genre)
            }
        )
    }
}