package com.example.newsreaderapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsreaderapp.R
import com.example.newsreaderapp.ui.composables.NewsListScreen
import com.example.newsreaderapp.ui.composables.ShowArticleScreen
import com.example.newsreaderapp.ui.composables.elements.SpeechButton
import com.example.newsreaderapp.ui.composables.elements.TopBar
import com.example.newsreaderapp.ui.theme.NewsreaderappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowNewsActivity : ComponentActivity() {

    private val viewModel: ShowNewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsreaderappTheme {
                Scaffold(
                    topBar = {
                        TopBar(getString(R.string.news), viewModel)
                    },
                    floatingActionButton = {
                        SpeechButton(viewModel)
                    }
                ) { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = "newsList") {
                            composable("newsList") {
                                NewsListScreen(navController, viewModel)
                            }
                            composable("newsDetail") {
                                ShowArticleScreen(viewModel)
                            }
                        }
                    }
                }
            }
        }
    }

}
