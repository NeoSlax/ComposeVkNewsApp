package ru.neoslax.composevknewsapp.ui

import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.domain.model.PostComment

sealed class HomeScreenState {

    object Initial : HomeScreenState()

    data class Feed(val feedItems: List<FeedItem>) : HomeScreenState()

    data class Comments(val feedItem: FeedItem, val comments: List<PostComment>) : HomeScreenState()

}
