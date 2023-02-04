package ru.neoslax.composevknewsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.domain.model.StatisticsItem

class MainViewModel : ViewModel() {

    val feedItemsList = mutableListOf<FeedItem>().apply {
        repeat(10) {
            add(FeedItem(id = it))
        }
    }

    private val initialState: FeedScreenState.Feed = FeedScreenState.Feed(feedItemsList)

    private val _screenState = MutableLiveData<FeedScreenState>(initialState)
    val screenState: LiveData<FeedScreenState> = _screenState

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