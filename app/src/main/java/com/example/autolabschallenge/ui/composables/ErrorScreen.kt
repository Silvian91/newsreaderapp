package com.example.autolabschallenge.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autolabschallenge.R
import com.example.autolabschallenge.ui.ShowNewsViewModel
import kotlinx.coroutines.launch

@Composable
fun ErrorScreen(
    viewModel: ShowNewsViewModel = hiltViewModel(),
    errorText: String
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = errorText)
        Button(onClick = {
            coroutineScope.launch {
                viewModel.getNews()
            }
        }) {
            Text(stringResource(R.string.retry))
        }
    }
}
