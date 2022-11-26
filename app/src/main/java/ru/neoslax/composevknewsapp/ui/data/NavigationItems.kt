package ru.neoslax.composevknewsapp.ui.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import ru.neoslax.composevknewsapp.R

sealed class NavigationItems(
    val icon: ImageVector,
    @StringRes val label: Int,
) {
    object Main : NavigationItems(Icons.Filled.Home, R.string.navigation_label_home)
    object Favourites : NavigationItems(Icons.Filled.Favorite, R.string.navigation_label_favourite)
    object Profile : NavigationItems(Icons.Filled.Person, R.string.navigation_label_profile)
}
