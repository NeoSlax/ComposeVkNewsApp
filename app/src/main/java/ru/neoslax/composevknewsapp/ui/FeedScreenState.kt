package ru.neoslax.composevknewsapp.ui

import ru.neoslax.composevknewsapp.domain.model.FeedItem

sealed class FeedScreenState {

    object Initial : FeedScreenState()

    data class Feed(val feedItems: List<FeedItem>) : FeedScreenState()

}
