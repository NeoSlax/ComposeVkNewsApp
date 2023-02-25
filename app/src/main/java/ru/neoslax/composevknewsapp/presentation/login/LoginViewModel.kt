package ru.neoslax.composevknewsapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult

class LoginViewModel : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>(LoginState.Initial)
    val loginState: LiveData<LoginState> = _loginState

    init {
        _loginState.value = if (VK.isLoggedIn()) LoginState.Authorized else LoginState.NotAuthorized
    }

    fun login(authResult: VKAuthenticationResult) {
        when (authResult) {
            is VKAuthenticationResult.Success -> {
                _loginState.value = LoginState.Authorized
            }
            else -> _loginState.value = LoginState.NotAuthorized
        }
    }
}

sealed interface LoginState {
    object Initial : LoginState
    object NotAuthorized : LoginState
    object Authorized : LoginState
}