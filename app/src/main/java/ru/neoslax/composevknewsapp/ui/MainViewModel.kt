package ru.neoslax.composevknewsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.domain.model.PostComment
import ru.neoslax.composevknewsapp.domain.model.StatisticsItem

class MainViewModel : ViewModel() {

    val feedItemsList = mutableListOf<FeedItem>().apply {
        repeat(10) {
            add(FeedItem(id = it))
        }
    }

    val localComments = mutableListOf<PostComment>().apply {
        repeat(10) {
            add(PostComment(it))
        }
    }

    private val initialState: HomeScreenState.Feed = HomeScreenState.Feed(feedItemsList)

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState> = _screenState

    private var previousScreenState: HomeScreenState? = initialState
    fun onCommentButtonClicked(feedItem: FeedItem) {
        previousScreenState = screenState.value
        _screenState.value = HomeScreenState.Comments(feedItem, localComments)
    }

    fun onCloseCommentsClick() {
        _screenState.value = previousScreenState
    }
    fun updateCounter(feedItem: FeedItem, statisticsItem: StatisticsItem) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Feed) return
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
        _screenState.value = HomeScreenState.Feed(feedItems = feedList)
    }

    fun deleteItem(feedItem: FeedItem) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Feed) return
        val feedList = requireNotNull(currentState.feedItems).toMutableList()
        feedList.remove(feedItem)
        _screenState.value = HomeScreenState.Feed(feedItems = feedList)

    }
}