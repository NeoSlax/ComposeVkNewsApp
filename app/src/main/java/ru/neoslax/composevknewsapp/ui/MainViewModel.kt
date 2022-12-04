package ru.neoslax.composevknewsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.domain.model.StatisticsItem
import ru.neoslax.composevknewsapp.ui.data.NavigationItems

class MainViewModel : ViewModel() {

    val list = mutableListOf<FeedItem>().apply {
        repeat(10) {
            add(FeedItem(id = it))
        }
    }

    private val _feedItems = MutableLiveData(list.toList())
    val feedItems: LiveData<List<FeedItem>> = _feedItems

    private val _navigation = MutableLiveData<NavigationItems>(NavigationItems.Main)
    val navigation: LiveData<NavigationItems> = _navigation

    fun updateNavigation(navItem: NavigationItems) {
        _navigation.value = navItem
    }

    fun updateCounter(feedItem: FeedItem, statisticsItem: StatisticsItem) {
        val feedList = requireNotNull(feedItems.value).toMutableList()
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
        _feedItems.value = feedList
    }

    fun deleteItem(feedItem: FeedItem) {
        val feedList = requireNotNull(feedItems.value).toMutableList()
        feedList.remove(feedItem)
        _feedItems.value = feedList

    }
}