package ru.neoslax.composevknewsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult.Failed
import com.vk.api.sdk.auth.VKAuthenticationResult.Success
import com.vk.api.sdk.auth.VKScope.WALL
import ru.neoslax.composevknewsapp.ui.MainViewModel
import ru.neoslax.composevknewsapp.ui.theme.ComposeVkNewsAppTheme
import ru.neoslax.composevknewsapp.ui.view.MainScreen

class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeVkNewsAppTheme {

                val launcher = rememberLauncherForActivityResult(contract = VK.getVKAuthActivityResultContract()) {
                    when(it) {
                        is Failed  -> {

                        }
                        is Success -> {
                            Log.d("TAG","Success")
                        }
                    }
                }

                launcher.launch(listOf(WALL))

                MainScreen(viewModel)
            }
            LazyColumn {}
        }
    }
}

