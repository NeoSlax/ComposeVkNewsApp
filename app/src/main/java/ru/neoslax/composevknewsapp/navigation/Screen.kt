package ru.neoslax.composevknewsapp.navigation

import android.net.Uri
import com.google.gson.Gson
import ru.neoslax.composevknewsapp.domain.model.FeedItem

sealed class Screen(val route: String) {

    object Home : Screen(Routes.HOME)
    object Comments : Screen(Routes.COMMENTS) {

        private const val ROUTE = "comments"

        fun getRoute(feedItem: FeedItem): String {
            return "$ROUTE/${Gson().toJson(feedItem).encode()}"
        }

    }
    object NewsFeed : Screen(Routes.NEWS_FEED)
    object Favourite : Screen(Routes.FAVOURITE)
    object Profile : Screen(Routes.PROFILE)

    private object Routes {

        const val HOME = "home"
        const val COMMENTS = "comments/{$KEY_FEED_ITEM}"
        const val NEWS_FEED = "news_feed"
        const val FAVOURITE = "favourite"
        const val PROFILE = "profile"
    }

    companion object {
        const val KEY_FEED_ITEM = "feed_item"
    }
}

fun String.encode(): String {
    return Uri.encode(this)
}
