package ru.neoslax.composevknewsapp.presentation.news

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.neoslax.composevknewsapp.R
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.domain.model.ItemType
import ru.neoslax.composevknewsapp.domain.model.StatisticsItem

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    feedItem: FeedItem,
    onCommentsItemClick: (FeedItem) -> Unit,
    onLikesItemClick: (FeedItem, StatisticsItem) -> Unit
) {

    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(6.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    model = feedItem.avatarUrl,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = feedItem.postTitle)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = feedItem.postTime, color = MaterialTheme.colors.onSecondary)
                }
                Icon(
                    imageVector = Icons.Rounded.MoreVert, contentDescription = "",
                    tint = MaterialTheme.colors.onSecondary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentScale = ContentScale.FillWidth,
                model = feedItem.post,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistics(
                items = feedItem.postStatistics,
                onCommentsItemClick = { onCommentsItemClick(feedItem) },
                onLikesItemClick = { onLikesItemClick(feedItem, it) },
                isLiked = feedItem.isLiked
            )
        }
    }
}

@Composable
private fun Statistics(
    items: List<StatisticsItem>,
    onCommentsItemClick: (StatisticsItem) -> Unit,
    onLikesItemClick: (StatisticsItem) -> Unit,
    isLiked: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),

        ) {
        Row(modifier = Modifier.weight(1f)) {
            val views = items.getItemByType(ItemType.VIEWS)
            Counters(
                count = views.value
            )
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            val reposts = items.getItemByType(ItemType.REPOSTS)
            val comments = items.getItemByType(ItemType.COMMENTS)
            val likes = items.getItemByType(ItemType.LIKES)
            Counters(
                count = reposts.value,
                drawable = R.drawable.ic_share
            )
            Counters(
                onItemClick = { onCommentsItemClick(comments) },
                count = comments.value,
                drawable = R.drawable.ic_comment
            )
            Counters(
                onItemClick = { onLikesItemClick(likes) },
                count = likes.value,
                drawable = if (isLiked) R.drawable.ic_like_set else R.drawable.ic_like
            )
        }
    }
}

fun List<StatisticsItem>.getItemByType(type: ItemType): StatisticsItem {
    return requireNotNull(this.find { it.itemType == type })
}

@Composable
fun Counters(
    onItemClick: (() -> Unit)? = null,
    count: Int = 100,
    @DrawableRes drawable: Int = R.drawable.ic_views_count
) {

    val clickModifier = if (onItemClick == null) {
        Modifier
    } else {
        Modifier.clickable(onClick = onItemClick)
    }

    Row(
        modifier = clickModifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = getReadableCountValue(count), color = MaterialTheme.colors.onSecondary)
        Spacer(modifier = Modifier.width(6.dp))
        Image(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = drawable),
            contentDescription = ""
        )
    }
}

private fun getReadableCountValue(input: Int): String {
    return if (input > 1_000_000) {
        String.format("%sK", input / 1_000)
    } else if (input > 1_000) {
        String.format("%.1fK", input / 1_000f)
    } else {
        input.toString()
    }
}