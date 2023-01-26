package ru.neoslax.composevknewsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavState(
    val navHostController: NavHostController
) {

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            popUpTo(Screen.NewsFeed.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

@Composable
fun rememberNavState(
    navHostController: NavHostController = rememberNavController()
): NavState {
    return NavState(
        navHostController
    )
}