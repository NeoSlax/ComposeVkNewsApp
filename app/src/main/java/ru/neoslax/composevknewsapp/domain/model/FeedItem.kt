@file:Suppress("DEPRECATION")

package ru.neoslax.composevknewsapp.domain.model

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedItem(
    val id: Long,
    val avatarUrl: String,
    val postTitle: String,
    val postTime: String,
    val postText: String,
    val post: String,
    val postStatistics: List<StatisticsItem>,
    val isLiked: Boolean,
    val communityId: Long
) : Parcelable {

    companion object {
        val feedItemNavType = object : NavType<FeedItem>(false) {
            override fun get(bundle: Bundle, key: String): FeedItem? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): FeedItem {
                return Gson().fromJson(value, FeedItem::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: FeedItem) {
                bundle.putParcelable(key, value)
            }
        }
    }
}
