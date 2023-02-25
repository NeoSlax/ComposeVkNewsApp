package ru.neoslax.composevknewsapp.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.neoslax.composevknewsapp.R
import ru.neoslax.composevknewsapp.ui.theme.VkMainColor

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.vk_logo),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(100.dp))
            Button(onClick = onLoginClick, colors = ButtonDefaults.buttonColors(backgroundColor = VkMainColor)) {
                Text(text = "Login", color = Color.White)
            }
        }
    }
}