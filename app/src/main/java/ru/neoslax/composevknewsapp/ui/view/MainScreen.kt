package ru.neoslax.composevknewsapp.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.domain.model.StatisticsItem
import ru.neoslax.composevknewsapp.ui.MainViewModel
import ru.neoslax.composevknewsapp.ui.data.NavigationItems


@Composable
fun MainScreen(viewModel: MainViewModel) {

    val feedItem = viewModel.feedItem.observeAsState(initial = FeedItem())

    Scaffold(
        bottomBar = {
            BottomAppBar {

                val selectedItem = remember {
                    mutableStateOf(0)
                }

                val items = listOf(
                    NavigationItems.Main,
                    NavigationItems.Favourites,
                    NavigationItems.Profile
                )
                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        selected = index == selectedItem.value,
                        onClick = {
                            selectedItem.value = index
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
        NewsCard(
            modifier = Modifier.padding(8.dp),
            feedItem.value,
            onViewItemClick = viewModel::updateCounter,
            onRepostsItemClick = viewModel::updateCounter,
            onCommentsItemClick = viewModel::updateCounter,
            onLikesItemClick = viewModel::updateCounter,
        )
    }
}