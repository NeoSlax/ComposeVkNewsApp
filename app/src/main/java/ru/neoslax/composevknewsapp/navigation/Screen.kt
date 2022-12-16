package ru.neoslax.composevknewsapp.navigation

sealed class Screen(val route: String) {

    object NewsFeed : Screen(Routes.NEWS_FEED)
    object Favourite : Screen(Routes.FAVOURITE)
    object Profile : Screen(Routes.PROFILE)

    private object Routes {
        const val NEWS_FEED = "news_feed"
        const val FAVOURITE = "favourite"
        const val PROFILE = "profile"
    }
}
