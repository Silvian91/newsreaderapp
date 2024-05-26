package com.example.autolabschallenge.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.autolabschallenge.data.model.ArticlesModel
import com.example.autolabschallenge.ui.ShowNewsViewModel
import com.example.autolabschallenge.ui.composables.elements.ArticleImage
import com.example.autolabschallenge.ui.composables.elements.ArticleTitle

@Composable
fun ShowNewsScreen(
    viewModel: ShowNewsViewModel = hiltViewModel(),
    articles: List<ArticlesModel>,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(articles) { article ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            viewModel.setArticle(article)
                            navController.navigate("newsDetail")
                        },
                ) {
                    Column {
                        article.urlToImage?.let {
                            ArticleImage(it)
                        }
                        ArticleTitle(article.source.name, article.title)
                    }
                }
            }
        }
    }
}



