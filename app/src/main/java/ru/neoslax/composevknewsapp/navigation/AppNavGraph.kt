package ru.neoslax.composevknewsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    homeScreen: @Composable () -> Unit,
    favouriteScreen: @Composable () -> Unit,
    profileScreen: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.NewsFeed.route
    ) {
        composable(Screen.NewsFeed.route) {
            homeScreen()
        }
        composable(Screen.Favourite.route) {
            favouriteScreen()
        }
        composable(Screen.Profile.route) {
            profileScreen()
        }
    }
}