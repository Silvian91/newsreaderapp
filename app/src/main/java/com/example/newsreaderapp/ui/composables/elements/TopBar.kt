package com.example.newsreaderapp.ui.composables.elements

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsreaderapp.R
import com.example.newsreaderapp.ui.ShowNewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    viewModel: ShowNewsViewModel = hiltViewModel()
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        ),
        actions = {
            IconButton(onClick = { isMenuExpanded = !isMenuExpanded }) {
                Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.menu), tint = Color.White)
            }
            DropdownMenu(
                expanded = isMenuExpanded,
                onDismissRequest = { isMenuExpanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    viewModel.sortNews()
                    isMenuExpanded = false
                },
                    text = { Text(stringResource(R.string.sort_published_date)) }
                )
            }
        }
    )
}
