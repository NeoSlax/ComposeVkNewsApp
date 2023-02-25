package ru.neoslax.composevknewsapp.presentation.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.domain.model.PostComment

class CommentsViewModel(
    feedItem: FeedItem
) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(feedItem)
    }

    private fun loadComments(feedItem: FeedItem) {
        val localComments = mutableListOf<PostComment>().apply {
            repeat(10) {
                add(PostComment(it))
            }
        }
        _screenState.value = CommentsScreenState.Comments(feedItem, localComments)
    }
}