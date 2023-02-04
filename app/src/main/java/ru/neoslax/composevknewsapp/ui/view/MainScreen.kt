package ru.neoslax.composevknewsapp.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.navigation.AppNavGraph
import ru.neoslax.composevknewsapp.navigation.rememberNavState
import ru.neoslax.composevknewsapp.ui.CommentsScreen
import ru.neoslax.composevknewsapp.ui.HomeScreen
import ru.neoslax.composevknewsapp.ui.data.NavigationItems

@Composable
fun MainScreen() {

    val navigationState = rememberNavState()

    val commentsToPost: MutableState<FeedItem?> = remember {
        mutableStateOf(null)
    }

    Scaffold(
        bottomBar = {
            BottomAppBar {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val items = listOf(
                    NavigationItems.Main,
                    NavigationItems.Favourites,
                    NavigationItems.Profile
                )
                items.forEach { item ->
                    BottomNavigationItem(
                        modifier = Modifier.weight(1f),
                        selected = item.screen.route == currentRoute,
                        onClick = {
                            navigationState.navigateTo(item.screen.route)
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
            homeScreen = {
                if (commentsToPost.value == null)
                    HomeScreen(paddingValues = paddingVal, onCommentsClickListener = {
                        commentsToPost.value = it
                    }) else {
                    CommentsScreen(
                        feedItem = commentsToPost.value!!
                    ) {
                        commentsToPost.value = null
                    }
                }
            },
            favouriteScreen = { Counter(text = "Fav") },
            profileScreen = { Counter(text = "Profile") })
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