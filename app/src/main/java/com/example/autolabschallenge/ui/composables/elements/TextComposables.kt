package com.example.autolabschallenge.ui.composables.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ArticleTitle(title: String) {
    Text(
        title,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(8.dp),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ArticleScreenTitle(title: String){
    Text(
        title,
        modifier = Modifier
            .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 12.dp),
        style = TextStyle(
            color = Color.Black,
            fontSize = 24.0.sp,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun ArticleScreenContent(content: String){
    Text(
        content,
        modifier = Modifier
            .padding(start = 12.dp, top = 8.dp, end = 12.dp),
        style = TextStyle(
            color = Color.Black,
            fontSize = 18.0.sp
        )
    )
}
