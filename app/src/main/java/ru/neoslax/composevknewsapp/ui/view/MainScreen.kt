package ru.neoslax.composevknewsapp.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.neoslax.composevknewsapp.ui.HomeScreen
import ru.neoslax.composevknewsapp.ui.MainViewModel
import ru.neoslax.composevknewsapp.ui.data.NavigationItems

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val selectedNavItem by viewModel.navigation.observeAsState(initial = NavigationItems.Main)

    Scaffold(
        bottomBar = {
            BottomAppBar {
                val items = listOf(
                    NavigationItems.Main,
                    NavigationItems.Favourites,
                    NavigationItems.Profile
                )
                items.forEach { item ->
                    BottomNavigationItem(
                        selected = item == selectedNavItem,
                        onClick = {
                            viewModel.updateNavigation(item)
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
    ) {
        val selectedNav = viewModel.navigation.observeAsState(NavigationItems.Main)
        when (selectedNav.value) {
            NavigationItems.Favourites -> Counter(text = "Fav")
            NavigationItems.Main -> HomeScreen(viewModel = viewModel, paddingValues = it)
            NavigationItems.Profile -> Counter(text = "Profile")
        }
    }
}

@Composable
private fun Counter(text: String) {
    var counter by remember {
        mutableStateOf(0)
    }
    Text(text = "$text: $counter", modifier = Modifier.clickable {
        counter++
    })
}