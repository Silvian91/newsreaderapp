package com.example.newsreaderapp.ui.composables.elements

import SpeechRecognizerManager
import android.Manifest
import android.content.pm.PackageManager
import android.view.MotionEvent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsreaderapp.R
import com.example.newsreaderapp.ui.ShowNewsViewModel
import com.example.newsreaderapp.ui.theme.Purple40

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SpeechButton(
    viewModel: ShowNewsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val speechRecognizerManager = remember {
        SpeechRecognizerManager(context) { voiceCommand ->
            viewModel.setVoiceCommand(voiceCommand)
        }
    }
    var permissionGranted by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionGranted = isGranted
    }

    permissionGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED

    DisposableEffect(Unit) {
        onDispose {
            speechRecognizerManager.destroy()
        }
    }

    FloatingActionButton(
        onClick = { },
        containerColor = Purple40,
        modifier = Modifier
            .padding(bottom = 28.dp, end = 28.dp)
            .pointerInteropFilter {
                if (permissionGranted) {
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
                } else {
                    launcher.launch(Manifest.permission.RECORD_AUDIO)
                    true
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
