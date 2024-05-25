package com.example.autolabschallenge.ui.composables.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ArticleImage(
    newsImageUrl: String
) {
    Image(
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth(),
        painter = rememberAsyncImagePainter(newsImageUrl),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}
