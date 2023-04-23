package ru.neoslax.composevknewsapp.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.neoslax.composevknewsapp.data.repository.NewsRepository
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.presentation.news.FeedScreenState

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsRepository(application)

    private val initialState: FeedScreenState = FeedScreenState.Initial

    private val _screenState = MutableLiveData(initialState)

    val screenState: LiveData<FeedScreenState> = _screenState

    init {
        loadRecommendations()
    }

    private fun loadRecommendations() {
        _screenState.value = FeedScreenState.Loading
        viewModelScope.launch {
            repository.loadData()
            val data = repository.feedItems
            val state = FeedScreenState.Feed(data, false)
            _screenState.value = state
        }
    }

    fun loadNextRecommendation() {
        val state = FeedScreenState.Feed(repository.feedItems, true)
        _screenState.value = state
        loadRecommendations()
    }

    fun changeLikeStatus(feedItem: FeedItem) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedItem)
            val data = repository.feedItems
            val state = FeedScreenState.Feed(data)
            _screenState.value = state
        }
    }

    fun deleteItem(feedItem: FeedItem) {
        val currentState = screenState.value
        if (currentState !is FeedScreenState.Feed) return

        viewModelScope.launch {
            repository.deleteNewsItem(feedItem)
            val feedList = repository.feedItems
            _screenState.value = FeedScreenState.Feed(feedItems = feedList)

        }


    }
}