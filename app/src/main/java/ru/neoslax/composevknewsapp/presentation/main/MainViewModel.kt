package ru.neoslax.composevknewsapp.presentation.main

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.neoslax.composevknewsapp.data.repository.NewsRepository
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.domain.model.StatisticsItem
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
        viewModelScope.launch {
            repository.loadData()
            val data = repository.feedItems
            val state = FeedScreenState.Feed(data)
            _screenState.value = state
        }
    }

    fun changeLikeStatus(feedItem: FeedItem) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedItem)
            val data = repository.feedItems
            val state = FeedScreenState.Feed(data)
            _screenState.value = state
        }
    }

    fun updateCounter(feedItem: FeedItem, statisticsItem: StatisticsItem) {
        val currentState = screenState.value
        if (currentState !is FeedScreenState.Feed) return
        val feedList = requireNotNull(currentState.feedItems).toMutableList()
        val oldStats = feedItem.postStatistics
        val newStats = oldStats.toMutableList().apply {
            replaceAll { statsItem ->
                if (statsItem == statisticsItem) {
                    statsItem.copy(value = statsItem.value + 1)
                } else {
                    statsItem
                }
            }
        }
        feedList.replaceAll {
            if (it.id == feedItem.id) {
                feedItem.copy(postStatistics = newStats)
            } else {
                it
            }
        }
        _screenState.value = FeedScreenState.Feed(feedItems = feedList)
    }

    fun deleteItem(feedItem: FeedItem) {
        val currentState = screenState.value
        if (currentState !is FeedScreenState.Feed) return
        val feedList = requireNotNull(currentState.feedItems).toMutableList()
        feedList.remove(feedItem)
        _screenState.value = FeedScreenState.Feed(feedItems = feedList)

    }
}