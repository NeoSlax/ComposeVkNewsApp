package ru.neoslax.composevknewsapp.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.neoslax.composevknewsapp.navigation.AppNavGraph
import ru.neoslax.composevknewsapp.navigation.Screen
import ru.neoslax.composevknewsapp.ui.HomeScreen
import ru.neoslax.composevknewsapp.ui.MainViewModel
import ru.neoslax.composevknewsapp.ui.data.NavigationItems

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
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
                            navHostController.navigate(item.screen.route) {
                                popUpTo(Screen.NewsFeed.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
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
            navHostController = navHostController,
            homeScreen = { HomeScreen(viewModel = viewModel, paddingValues = paddingVal) },
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