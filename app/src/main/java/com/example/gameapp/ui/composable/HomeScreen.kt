package com.example.gameapp.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.gameapp.model.response.Game
import com.example.gameapp.navigation.Screen
import com.example.gameapp.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<HomeViewModel>()
    val genres by viewModel.savedGenres.collectAsStateWithLifecycle()
    val selectedGenre by viewModel.selectedGenre.collectAsStateWithLifecycle()

    val games by remember { viewModel.games }
    val isLoading by remember { viewModel.isLoading }
    val loadError by remember { viewModel.loadError }
    val interactionSource = remember { MutableInteractionSource() }

    Column {
        TopBar(shouldShowOptions = true, navController = navController)
        if (genres.isEmpty()) {
            // Handle this in a way that you can't finish Onboarding without selecting a genre
            Text(text = "There are no saved genres")
        } else {
            if (selectedGenre.isEmpty()) viewModel.setSelectedGenre(genres[0].name)
            Column(modifier = modifier.fillMaxSize()) {
                LazyRow(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    items(genres.size, itemContent = {
                        Box(
                            modifier = modifier.clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                viewModel.setSelectedGenre(genres[it].name)
                            }

                        ) {
                            Text(
                                text = genres[it].name,
                                fontWeight = if (genres[it].name == selectedGenre) FontWeight.Bold else FontWeight.Normal,
                                fontSize = if (genres[it].name == selectedGenre) 18.sp else 16.sp,
                                modifier = modifier.padding(16.dp)
                            )
                        }
                    })
                }
                if (isLoading) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.2f)
                    ) {
                        CircularProgressIndicator(
                            color = Color.Black,
                            modifier = modifier.align(Alignment.Center)
                        )
                    }
                } else {
                    LazyColumn(modifier = modifier.fillMaxSize()) {
                        items(games.size, itemContent = {
                            GameEntry(game = games[it], navController = navController)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun GameEntry(
    game: Game,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(12.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(Color.White)
            .clickable {
                navController.navigate(
                    "${Screen.GameDetails.route}/${game.id.toString()}"
                )
            }
    ) {
        Column {
            SubcomposeAsyncImage(
                contentScale = ContentScale.FillBounds,
                model = game.backgroundImage,
                contentDescription = game.name,
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .align(Alignment.CenterHorizontally),
                loading = {
                    Box(modifier = modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            color = Color.Black,
                            modifier = modifier.align(Alignment.Center)
                        )
                    }
                }
            )
            Box(
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = game.name ?: "",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    modifier = modifier.align(Alignment.Center)
                )
            }
        }
    }
}