package ru.neoslax.composevknewsapp.data.mapper

import ru.neoslax.composevknewsapp.data.model.CommentsResponseDto
import ru.neoslax.composevknewsapp.data.model.VKNewsFeedResponseDto
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.domain.model.ItemType
import ru.neoslax.composevknewsapp.domain.model.PostComment
import ru.neoslax.composevknewsapp.domain.model.StatisticsItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

fun VKNewsFeedResponseDto.toPosts(): List<FeedItem> {
    val result = mutableListOf<FeedItem>()
    val posts = this.response.items
    val groups = this.response.groups

    for (post in posts) {
        val group = groups.find { it.id == post.communityId.absoluteValue} ?: continue
        val feedItem = FeedItem(
            id = post.id,
            avatarUrl = group.groupImageUrl,
            postTitle = group.name,
            postTime = mapLongToDate(post.date),
            postText = post.text,
            post = post.attachmentsDto.firstOrNull()?.photoUrls?.photos?.lastOrNull()?.url ?: "",
            postStatistics = listOf(
                StatisticsItem(ItemType.LIKES, post.likes.count),
                StatisticsItem(ItemType.REPOSTS, post.reposts.count),
                StatisticsItem(ItemType.VIEWS, post.views.count),
                StatisticsItem(ItemType.COMMENTS, post.comments.count)
            ),
            isLiked = post.likes.isLiked > 0,
            communityId = post.communityId
        )
        result.add(feedItem)
    }
    return result
}

fun CommentsResponseDto.mapToComments(): List<PostComment> {
    val result = mutableListOf<PostComment>()
    val profiles = this.response.profiles
    val comments = this.response.comments

    for (comment in comments) {
        if (comment.text.isBlank()) continue
        val profile = profiles.firstOrNull { it.id == comment.authorId } ?: continue
        val postComment = PostComment(
            id = comment.id,
            userPhotoUrl = profile.avatarUrl,
            authorName = "${profile.firstName} ${profile.lastName}",
            commentTime = mapLongToDate(comment.date ),
            authorComment = comment.text
        )
        result.add(postComment)
    }
    return result
}

private fun mapLongToDate(longDate: Long): String {
    val date = Date(longDate * 1_000)
    return SimpleDateFormat("d MMMM yyyy, HH:MM", Locale.getDefault()).format(date)
}