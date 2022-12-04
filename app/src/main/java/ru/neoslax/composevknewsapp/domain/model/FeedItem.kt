package ru.neoslax.composevknewsapp.domain.model

import ru.neoslax.composevknewsapp.R

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
)
