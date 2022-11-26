package ru.neoslax.composevknewsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.domain.model.StatisticsItem

class MainViewModel : ViewModel() {

    private val _feedItem = MutableLiveData(FeedItem())
    val feedItem: LiveData<FeedItem> = _feedItem

    fun updateCounter(statisticsItem: StatisticsItem) {
        val feedItem = requireNotNull(_feedItem.value)
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
        _feedItem.value = feedItem.copy(postStatistics = newStats)
    }
}