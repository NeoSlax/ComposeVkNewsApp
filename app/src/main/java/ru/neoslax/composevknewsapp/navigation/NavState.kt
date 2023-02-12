package ru.neoslax.composevknewsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.neoslax.composevknewsapp.domain.model.FeedItem

class NavState(
    val navHostController: NavHostController
) {

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToComments(feedItem: FeedItem) {
        navHostController.navigate(Screen.Comments.getRoute(feedItem))
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