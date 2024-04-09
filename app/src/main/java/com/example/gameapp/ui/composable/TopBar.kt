package com.example.gameapp.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import com.example.gameapp.R
import com.example.gameapp.navigation.Screen

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.gameapp),
    shouldShowShadow: Boolean = true,
    shouldShowOptions: Boolean = false,
    navController: NavController? = null
) {
    Column(modifier = modifier
        .fillMaxWidth(),
    ) {
        Box (
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.08f),
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                modifier = modifier.align(Alignment.Center)
            )
            if (shouldShowOptions) {
                IconButton(
                    modifier = modifier.align(Alignment.CenterEnd),
                    onClick = {
                        navController?.let {
                            navController.navigate(Screen.Onboarding.route)
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = null, tint = Color.Black)
                }
            }
        }
        if (shouldShowShadow) {
            BottomShadow()
        }
    }
}

@Composable
fun BottomShadow(alpha: Float = 0.1f, height: Dp = 8.dp) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(height)
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Black.copy(alpha = alpha),
                    Color.Transparent,
                )
            )
        )
    )
}