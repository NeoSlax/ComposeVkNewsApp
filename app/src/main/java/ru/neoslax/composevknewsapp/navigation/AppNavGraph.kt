package ru.neoslax.composevknewsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.neoslax.composevknewsapp.domain.model.FeedItem

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedItem) -> Unit,
    favouriteScreen: @Composable () -> Unit,
    profileScreen: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        homeScreenNavGraph(
            homeScreen = newsFeedScreenContent,
            commentsScreen = commentsScreenContent
        )
        composable(Screen.Favourite.route) {
            favouriteScreen()
        }
        composable(Screen.Profile.route) {
            profileScreen()
        }
    }
}

@Suppress("DEPRECATION")
fun NavGraphBuilder.homeScreenNavGraph(
    homeScreen: @Composable () -> Unit,
    commentsScreen: @Composable (FeedItem) -> Unit,
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            homeScreen()
        }
        composable(route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_ITEM) {
                    type = FeedItem.feedItemNavType
                }
            )
        ) {
            val feedItem = it.arguments?.getParcelable<FeedItem>(Screen.KEY_FEED_ITEM)
            checkNotNull(feedItem)
            commentsScreen(feedItem)
        }
    }
}
