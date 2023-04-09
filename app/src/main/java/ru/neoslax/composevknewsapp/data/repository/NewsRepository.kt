package ru.neoslax.composevknewsapp.data.repository

import android.app.Application
import android.util.Log
import com.vk.api.sdk.VKApiConfig
import com.vk.api.sdk.auth.VKAccessToken
import ru.neoslax.composevknewsapp.data.api.ApiFactory
import ru.neoslax.composevknewsapp.data.mapper.toPosts
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.domain.model.ItemType
import ru.neoslax.composevknewsapp.domain.model.StatisticsItem

class NewsRepository(val application: Application) {

    private val api = ApiFactory.api

    private val _feedItems = mutableListOf<FeedItem>()
    val feedItems: List<FeedItem>
        get() = _feedItems.toList()

    private var nextFrom: String? = null


    private fun getToken(): String {
        val vkApiConfig = VKApiConfig(application)
        val key = VKAccessToken.restore(vkApiConfig.keyValueStorage)
        Log.d(this.javaClass.name, "${key?.accessToken}")
        return key?.accessToken.toString()
    }

    suspend fun loadData() {
        val startFrom = nextFrom
        if (startFrom == null && feedItems.isNotEmpty()) return
        val response = if (startFrom != null) {
            api.loadNewsFeed(token = getToken(), startFrom)
        } else {
            api.loadNewsFeed(getToken())
        }
        nextFrom = response.response.nextFrom
        _feedItems.addAll(response.toPosts())
    }

    suspend fun changeLikeStatus(
        feedItem: FeedItem
    ) {
        val response = if (feedItem.isLiked) {
            api.deleteLike(
                token = getToken(),
                ownerId = feedItem.communityId,
                postId = feedItem.id
            )
        } else {
            api.addLike(
                token = getToken(),
                ownerId = feedItem.communityId,
                postId = feedItem.id
            )
        }
        val newLikeCount = response.response.count
        val newStats = feedItem.postStatistics.toMutableList()
            .apply {
                removeIf { it.itemType == ItemType.LIKES }
                add(StatisticsItem(itemType = ItemType.LIKES, newLikeCount))
            }
        val newItem = feedItem.copy(postStatistics = newStats, isLiked = !feedItem.isLiked)
        val index = _feedItems.indexOf(feedItem)
        _feedItems[index] = newItem
    }
}