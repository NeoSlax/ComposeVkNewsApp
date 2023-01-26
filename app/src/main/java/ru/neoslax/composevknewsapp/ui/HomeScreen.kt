package ru.neoslax.composevknewsapp.ui

import androidx.activity.compose.BackHandler
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
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.ui.view.NewsCard

@Composable
fun HomeScreen(viewModel: MainViewModel, paddingValues: PaddingValues) {

    val screenState = viewModel.screenState.observeAsState(initial = HomeScreenState.Initial)

    when (val localState = screenState.value) {
        is HomeScreenState.Comments -> {
            CommentsScreen(
                postId = localState.feedItem.id,
                commentList = localState.comments,
                onBackButtonClick = viewModel::onCloseCommentsClick
            )
            BackHandler {
                viewModel.onCloseCommentsClick()
            }
        }

        is HomeScreenState.Feed -> {
            FeedScreen(
                feedItems = localState.feedItems,
                paddingValues = paddingValues,
                viewModel = viewModel
            )
        }

        is HomeScreenState.Initial -> {

        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedScreen(
    feedItems: List<FeedItem>,
    paddingValues: PaddingValues,
    viewModel: MainViewModel
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
                    onCommentsItemClick = viewModel::onCommentButtonClicked,
                    onLikesItemClick = viewModel::updateCounter,
                )
            }
        }
    }
}