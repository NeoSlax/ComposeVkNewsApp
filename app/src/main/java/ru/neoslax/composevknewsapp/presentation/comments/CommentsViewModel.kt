package ru.neoslax.composevknewsapp.presentation.comments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.neoslax.composevknewsapp.data.repository.NewsRepository
import ru.neoslax.composevknewsapp.domain.model.FeedItem

class CommentsViewModel(
    feedItem: FeedItem,
    application: Application
) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    private val repository = NewsRepository(application)

    init {
        _screenState.value = CommentsScreenState.Loading
        loadComments(feedItem)
    }

    private fun loadComments(feedItem: FeedItem) {
        viewModelScope.launch {
            val loadedComments = repository.loadComments(feedItem)
            _screenState.value = CommentsScreenState.Comments(feedItem, loadedComments)
        }
    }
}