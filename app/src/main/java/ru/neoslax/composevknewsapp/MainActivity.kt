package ru.neoslax.composevknewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import ru.neoslax.composevknewsapp.ui.MainViewModel
import ru.neoslax.composevknewsapp.ui.theme.ComposeVkNewsAppTheme
import ru.neoslax.composevknewsapp.ui.view.MainScreen

class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeVkNewsAppTheme {
                MainScreen(viewModel)
            }
        }
    }
}

