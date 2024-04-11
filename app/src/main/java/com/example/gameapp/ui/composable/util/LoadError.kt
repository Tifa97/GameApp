package com.example.gameapp.ui.composable.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoadError(
    error: String,
    modifier: Modifier = Modifier
) {
    if (error.isNotEmpty()) {
        Box(modifier = modifier.fillMaxSize()) {
            Text(
                text = error,
                color = Color.Red,
                fontSize = 36.sp,
                lineHeight = 42.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 64.dp)
                    .offset(y = (-36).dp)
            )
        }
    }
}