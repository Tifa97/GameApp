package com.example.gameapp.ui.composable

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.gameapp.model.GenreItem
import com.example.gameapp.viewmodel.OnboardingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<OnboardingViewModel>()
    val genres by remember { viewModel.genres }
    val isLoading by remember { viewModel.isLoading }
    val loadError by remember { viewModel.loadError }
    val isOnboardingFinished by remember { viewModel.isOnboardingFinished }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxSize()) {
            TopBar()
            if (isLoading) {
                Box (modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier.align(Alignment.Center))
                }
            } else {
                LazyColumn(modifier = modifier.fillMaxHeight(0.9f)) {
                    items(genres.size, itemContent = { index ->
                        genres[index].id?.let { id ->
                            genres[index].name?.let {name ->
                                val genre = GenreItem(id, name, false)
                                GenreEntry(genre = genre, viewModel)
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
                            viewModel.finishOnboarding()
                        }
                    ) {
                        Text("Next")
                    }
                }
            }
        }
    }
}

@Composable
fun GenreEntry(genre: GenreItem, viewModel: OnboardingViewModel, modifier: Modifier = Modifier) {
    val isChecked = remember { mutableStateOf(genre.isSelected) }

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