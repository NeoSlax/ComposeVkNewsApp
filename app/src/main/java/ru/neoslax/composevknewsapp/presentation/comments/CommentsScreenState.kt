package ru.neoslax.composevknewsapp.presentation.comments

import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.domain.model.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    object Loading : CommentsScreenState()

    data class Comments(val feedItem: FeedItem, val comments: List<PostComment>) : CommentsScreenState()
}