package ru.neoslax.composevknewsapp.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.neoslax.composevknewsapp.navigation.AppNavGraph
import ru.neoslax.composevknewsapp.navigation.rememberNavState
import ru.neoslax.composevknewsapp.presentation.comments.CommentsScreen
import ru.neoslax.composevknewsapp.presentation.news.FeedScreen
import ru.neoslax.composevknewsapp.presentation.main.NavigationItems

@Composable
fun MainScreen() {

    val navigationState = rememberNavState()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                val items = listOf(
                    NavigationItems.Main,
                    NavigationItems.Favourites,
                    NavigationItems.Profile
                )
                items.forEach { item ->
                    val isSelected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false
                    BottomNavigationItem(
                        modifier = Modifier.weight(1f),
                        selected = isSelected,
                        onClick = {
                            if (!isSelected) {
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = null)
                        },
                        label = {
                            Text(stringResource(item.label))
                        }
                    )
                }
            }
        }
    ) { paddingVal ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                FeedScreen(paddingValues = paddingVal,
                    onCommentsClickListener = { feedItem ->
                        navigationState.navigateToComments(feedItem)
                    })
            },
            favouriteScreen = { Counter(text = "Fav") },
            profileScreen = { Counter(text = "Profile") },
            commentsScreenContent = { feedItem ->
                CommentsScreen(
                    feedItem = feedItem,
                ) {
                    navigationState.navHostController.popBackStack()
                }
            })
    }
}

@Composable
private fun Counter(text: String) {
    var counter by rememberSaveable {
        mutableStateOf(0)
    }
    Text(text = "$text: $counter", modifier = Modifier.clickable {
        counter++
    })
}