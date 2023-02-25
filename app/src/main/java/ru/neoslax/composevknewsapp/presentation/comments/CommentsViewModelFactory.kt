package ru.neoslax.composevknewsapp.presentation.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.neoslax.composevknewsapp.domain.model.FeedItem

class CommentsViewModelFactory(
    private val feedItem: FeedItem
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedItem) as T
    }
}