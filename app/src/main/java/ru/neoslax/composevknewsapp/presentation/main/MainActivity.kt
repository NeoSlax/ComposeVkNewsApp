package ru.neoslax.composevknewsapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import ru.neoslax.composevknewsapp.presentation.login.LoginScreen
import ru.neoslax.composevknewsapp.presentation.login.LoginState
import ru.neoslax.composevknewsapp.presentation.login.LoginViewModel
import ru.neoslax.composevknewsapp.ui.theme.ComposeVkNewsAppTheme
import ru.neoslax.composevknewsapp.ui.view.MainScreen

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeVkNewsAppTheme {
                val viewModel: LoginViewModel = viewModel()
                val loginState = viewModel.loginState.observeAsState(LoginState.Initial)
                val launcher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract(),
                    onResult = viewModel::login
                )

                when (loginState.value) {
                    is LoginState.Authorized -> MainScreen()
                    is LoginState.NotAuthorized -> LoginScreen {
                        launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                    }
                    LoginState.Initial -> {}
                }
            }
        }
    }
}

