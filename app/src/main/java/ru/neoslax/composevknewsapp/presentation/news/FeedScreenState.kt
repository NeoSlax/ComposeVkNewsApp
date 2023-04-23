package ru.neoslax.composevknewsapp.presentation.news

import ru.neoslax.composevknewsapp.domain.model.FeedItem

sealed class FeedScreenState {

    object Initial : FeedScreenState()

    object Loading : FeedScreenState()

    data class Feed(val feedItems: List<FeedItem>, val isDataLoading: Boolean = false) : FeedScreenState()

}
