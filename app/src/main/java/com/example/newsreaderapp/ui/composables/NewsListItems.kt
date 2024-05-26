package com.example.newsreaderapp.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsreaderapp.R
import com.example.newsreaderapp.ui.ShowNewsViewModel
import com.example.newsreaderapp.ui.composables.elements.ShowLoading

@Composable
fun NewsListScreen(
    navController: NavController,
    viewModel: ShowNewsViewModel = hiltViewModel()
) {
    val news by viewModel.newsList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getNews()
    }

    if (isLoading) {
        ShowLoading()
    } else {
        news?.let { safeNews ->
            if (safeNews.status == stringResource(R.string.ok) && safeNews.articles != null) {
                safeNews.articles.let {
                    if (it != null) {
                        ShowNewsScreen(viewModel, it, navController)
                    }
                }
            } else if (safeNews.status == stringResource(R.string.ok) && safeNews.articles == null) {
                ErrorScreen(viewModel, stringResource(R.string.error_no_news))
            }
        } ?: run {
            ErrorScreen(viewModel, stringResource(R.string.error_internet_connection))
        }
    }
}
