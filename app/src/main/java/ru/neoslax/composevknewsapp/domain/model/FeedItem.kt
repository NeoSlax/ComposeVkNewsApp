@file:Suppress("DEPRECATION")

package ru.neoslax.composevknewsapp.domain.model

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import ru.neoslax.composevknewsapp.R

@Parcelize
data class FeedItem(
    val id: Int = 0,
    val postItemLogo: Int = R.drawable.post_comunity_thumbnail,
    val postTitle: String = "/dev/null",
    val postTime: String = "14:00",
    val postText: String = "",
    val post: Int = R.drawable.post_content_image,
    val postStatistics: List<StatisticsItem> = listOf(
        StatisticsItem(ItemType.VIEWS, 100),
        StatisticsItem(ItemType.REPOSTS, 100),
        StatisticsItem(ItemType.COMMENTS, 100),
        StatisticsItem(ItemType.LIKES, 100),
    )
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
