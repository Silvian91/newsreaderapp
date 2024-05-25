package com.example.autolabschallenge.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autolabschallenge.ui.ShowNewsViewModel
import com.example.autolabschallenge.ui.composables.elements.ArticleImage
import com.example.autolabschallenge.ui.composables.elements.ArticleScreenContent
import com.example.autolabschallenge.ui.composables.elements.ArticleScreenTitle

@Composable
fun ShowArticleScreen(
    viewModel: ShowNewsViewModel = hiltViewModel(),
) {
    val article = viewModel.newsArticle.collectAsState().value

    article?.let {
        Column {
            article.urlToImage?.let {
                ArticleImage(newsImageUrl = it)
            }
            ArticleScreenTitle(article.title)
            ArticleScreenContent(article.content)
        }
    }
}
