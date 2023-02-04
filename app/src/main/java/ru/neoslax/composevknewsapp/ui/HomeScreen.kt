package ru.neoslax.composevknewsapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.ui.view.NewsCard

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onCommentsClickListener: (FeedItem) -> Unit
) {

    val viewModel: MainViewModel = viewModel()

    val screenState = viewModel.screenState.observeAsState(initial = FeedScreenState.Initial)

    when (val localState = screenState.value) {

        is FeedScreenState.Feed -> {
            FeedScreen(
                feedItems = localState.feedItems,
                paddingValues = paddingValues,
                viewModel = viewModel,
                onCommentsClickListener = onCommentsClickListener
            )
        }

        is FeedScreenState.Initial -> {

        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedScreen(
    feedItems: List<FeedItem>,
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    onCommentsClickListener: (FeedItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 72.dp
        ),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(feedItems, key = { it.id }) {
            val swipeToDismissState = rememberDismissState()
            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = swipeToDismissState,
                background = {},
                directions = setOf(DismissDirection.EndToStart)
            ) {
                if (swipeToDismissState.isDismissed(DismissDirection.EndToStart)) {
                    viewModel.deleteItem(it)
                }
                NewsCard(
                    feedItem = it,
                    onViewItemClick = viewModel::updateCounter,
                    onRepostsItemClick = viewModel::updateCounter,
                    onCommentsItemClick = onCommentsClickListener,
                    onLikesItemClick = viewModel::updateCounter,
                )
            }
        }
    }
}