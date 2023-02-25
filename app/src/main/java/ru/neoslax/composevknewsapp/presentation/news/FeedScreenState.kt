package ru.neoslax.composevknewsapp.presentation.news

import ru.neoslax.composevknewsapp.domain.model.FeedItem

sealed class FeedScreenState {

    object Initial : FeedScreenState()

    data class Feed(val feedItems: List<FeedItem>) : FeedScreenState()

}
