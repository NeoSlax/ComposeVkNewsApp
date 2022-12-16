package ru.neoslax.composevknewsapp.ui.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import ru.neoslax.composevknewsapp.R
import ru.neoslax.composevknewsapp.navigation.Screen

sealed class NavigationItems(
    val screen: Screen,
    val icon: ImageVector,
    @StringRes val label: Int,
) {
    object Main : NavigationItems(
        screen = Screen.NewsFeed,
        icon = Icons.Filled.Home,
        label = R.string.navigation_label_home
    )

    object Favourites : NavigationItems(
        screen = Screen.Favourite,
        icon = Icons.Filled.Favorite,
        label = R.string.navigation_label_favourite
    )

    object Profile : NavigationItems(
        screen = Screen.Profile,
        icon = Icons.Filled.Person,
        label = R.string.navigation_label_profile
    )
}
