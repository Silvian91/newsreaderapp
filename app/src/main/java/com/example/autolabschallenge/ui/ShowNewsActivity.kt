package com.example.autolabschallenge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.autolabschallenge.R
import com.example.autolabschallenge.ui.composables.ShowArticleScreen
import com.example.autolabschallenge.ui.composables.NewsListScreen
import com.example.autolabschallenge.ui.composables.elements.SpeechButton
import com.example.autolabschallenge.ui.theme.AutolabschallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowNewsActivity : ComponentActivity() {

    private val viewModel: ShowNewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AutolabschallengeTheme {
                Scaffold(
                    topBar = {
                        ComposeAppBar(getString(R.string.news))
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ComposeAppBar(title: String) {
        TopAppBar(
            title = { Text(text = title) },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White
            )
        )
    }
}