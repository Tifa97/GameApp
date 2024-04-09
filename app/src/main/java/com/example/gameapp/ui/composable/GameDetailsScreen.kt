package com.example.gameapp.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.gameapp.R
import com.example.gameapp.viewmodel.GameDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameDetailsScreen(
    gameId: String?,
    navController: NavController, 
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<GameDetailsViewModel>()
    val gameDetails by remember { viewModel.gameDetails }
    val isLoading by remember { viewModel.isLoading }
    val platforms by remember { viewModel.platformNames }

    val uriHandler = LocalUriHandler.current

    if (gameDetails == null) {
        gameId?.let {
            viewModel.getGameDetails(it)
        }
    }

    if (isLoading) {
        Box(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator(
                color = Color.Black,
                modifier = modifier.align(Alignment.Center)
            )
        }
    } else {
        Column(modifier = modifier.fillMaxSize()) {
            TopBar(text = gameDetails?.name ?: "", shouldShowShadow = false)
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.35f),
                model = gameDetails?.backgroundImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Column(modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)){
                if (platforms.isNotEmpty()) {
                    GameDetailsSection(
                        title = stringResource(R.string.platforms),
                        text = platforms
                    )
                }
                gameDetails?.rating?.let { rating ->
                    GameDetailsSection(
                        title = stringResource(R.string.rating),
                        text = rating.toString()
                    )
                }

                gameDetails?.released?.let { released ->
                    GameDetailsSection(
                        title = stringResource(R.string.released),
                        text = released
                    )
                }

                gameDetails?.playtime?.let { playtime ->
                    GameDetailsSection(
                        title = stringResource(R.string.playtime),
                        text = playtime.toString()
                    )
                }

                gameDetails?.website?.let { website ->
                    GameDetailsSection(
                        title = stringResource(R.string.website),
                        text =  website,
                        modifier = modifier.clickable {
                            uriHandler.openUri(website)
                        },
                        isLink = true
                    )
                }

                gameDetails?.description?.let { description ->
                    GameDetailsSection(
                        title = stringResource(R.string.description),
                        text =  description,
                        modifier = modifier.verticalScroll(rememberScrollState()),
                        isLast = true
                    )
                }
            }
        }
    }
}

@Composable
fun GameDetailsSection(
    title: String,
    text: String,
    modifier: Modifier = Modifier,
    isLink: Boolean = false,
    isLast: Boolean = false
) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
    Text(
        text = text,
        fontSize = 14.sp,
        modifier = modifier,
        color = if (isLink) Color.Blue else Color.Black
    )
    if (!isLast) {
        Spacer(modifier = Modifier
            .padding(vertical = 4.dp)
            .height(1.dp)
            .fillMaxWidth()
            .background(Color.LightGray))
    }
}
