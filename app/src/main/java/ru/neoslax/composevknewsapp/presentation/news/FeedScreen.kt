package ru.neoslax.composevknewsapp.presentation.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.presentation.main.MainViewModel
import ru.neoslax.composevknewsapp.ui.theme.VkMainColor

@Composable
fun FeedScreen(
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
                onCommentsClickListener = onCommentsClickListener,
                isDataLoading = localState.isDataLoading
            )
        }

        is FeedScreenState.Initial -> {}

        is FeedScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = VkMainColor)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedScreen(
    feedItems: List<FeedItem>,
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    onCommentsClickListener: (FeedItem) -> Unit,
    isDataLoading: Boolean
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
                    onCommentsItemClick = onCommentsClickListener,
                    onLikesItemClick = { feedItem, _ -> viewModel.changeLikeStatus(feedItem) },
                )
            }
        }
        item {
            if (isDataLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = VkMainColor)
                }
            } else {
                SideEffect {
                    viewModel.loadNextRecommendation()
                }
            }

        }
    }
}