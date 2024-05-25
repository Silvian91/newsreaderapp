package com.example.autolabschallenge.ui

import SpeechRecognizerManager
import android.view.MotionEvent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.autolabschallenge.R
import com.example.autolabschallenge.data.model.ArticlesModel
import com.example.autolabschallenge.ui.theme.Purple40
import kotlinx.coroutines.coroutineScope

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

    val context = LocalContext.current

    val speechRecognizerManager = remember {
        SpeechRecognizerManager(context) { voiceCommand ->
            viewModel.setVoiceCommand(voiceCommand)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            speechRecognizerManager.destroy()
        }
    }

    if (isLoading) {
        ShowLoading()
    } else {
        if (news.status == "ok") {
            ShowNewsList(viewModel, news.articles, speechRecognizerManager, navController)
        } else {
            Text(text = "No places found")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowNewsList(
    viewModel: ShowNewsViewModel = hiltViewModel(),
    articles: List<ArticlesModel>,
    speechRecognizerManager: SpeechRecognizerManager,
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
                        ArticleTitle(article.title)
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = { },
            containerColor = Purple40,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 48.dp, end = 48.dp)
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            speechRecognizerManager.startListening()
                            true
                        }

                        MotionEvent.ACTION_UP -> {
                            speechRecognizerManager.stopListening()
                            true
                        }

                        else -> false
                    }
                },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_microphone),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
fun ShowLoading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun NewsDetailScreen(
    viewModel: ShowNewsViewModel = hiltViewModel(),
) {
    val article = viewModel.newsArticle.collectAsState().value

    Column {
        article.urlToImage?.let {
            Image(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

        }
        Text(
            article.title,
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 12.dp),
            style = TextStyle(
                color = Color.Black,
                fontSize = 24.0.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            article.content,
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp, end = 12.dp),
            style = TextStyle(
                color = Color.Black,
                fontSize = 18.0.sp
            )
        )
    }
}

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

@Composable
fun ArticleTitle(newsTitle: String) {
    Text(
        newsTitle,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(8.dp),
        fontWeight = FontWeight.Bold
    )
}