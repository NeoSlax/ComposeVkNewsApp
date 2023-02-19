package ru.neoslax.composevknewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.neoslax.composevknewsapp.ui.theme.ComposeVkNewsAppTheme
import ru.neoslax.composevknewsapp.ui.view.LoadImageScreen
import ru.neoslax.composevknewsapp.ui.view.MainScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeVkNewsAppTheme {
//                MainScreen()
                LoadImageScreen()
            }
        }
    }
}

