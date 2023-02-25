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
import ru.neoslax.composevknewsapp.R
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.domain.model.ItemType
import ru.neoslax.composevknewsapp.domain.model.StatisticsItem

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    feedItem: FeedItem,
    onViewItemClick: (FeedItem, StatisticsItem) -> Unit,
    onRepostsItemClick: (FeedItem, StatisticsItem) -> Unit,
    onCommentsItemClick: (FeedItem) -> Unit,
    onLikesItemClick: (FeedItem, StatisticsItem) -> Unit
) {

    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(6.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = feedItem.postItemLogo),
                    contentDescription = ""
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
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = feedItem.post),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistics(
                items = feedItem.postStatistics,
                onViewItemClick = { onViewItemClick(feedItem, it) },
                onRepostsItemClick = { onRepostsItemClick(feedItem, it) },
                onCommentsItemClick = { onCommentsItemClick(feedItem) },
                onLikesItemClick = { onLikesItemClick(feedItem, it) }
            )
        }
    }
}

@Composable
private fun Statistics(
    items: List<StatisticsItem>,
    onViewItemClick: (StatisticsItem) -> Unit,
    onRepostsItemClick: (StatisticsItem) -> Unit,
    onCommentsItemClick: (StatisticsItem) -> Unit,
    onLikesItemClick: (StatisticsItem) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),

        ) {
        Row(modifier = Modifier.weight(1f)) {
            val views = items.getItemByType(ItemType.VIEWS)
            Counters(
                count = views.value,
                onItemClick = { onViewItemClick(views) })
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            val reposts = items.getItemByType(ItemType.REPOSTS)
            val comments = items.getItemByType(ItemType.COMMENTS)
            val likes = items.getItemByType(ItemType.LIKES)
            Counters(
                onItemClick = { onRepostsItemClick(reposts) },
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
                drawable = R.drawable.ic_like
            )
        }
    }
}

fun List<StatisticsItem>.getItemByType(type: ItemType): StatisticsItem {
    return requireNotNull(this.find { it.itemType == type })
}

@Composable
fun Counters(
    onItemClick: () -> Unit,
    count: Int = 100,
    @DrawableRes drawable: Int = R.drawable.ic_views_count
) {
    Row(modifier = Modifier.clickable(onClick = onItemClick)) {
        Text(text = count.toString(), color = MaterialTheme.colors.onSecondary)
        Spacer(modifier = Modifier.width(6.dp))
        Image(painter = painterResource(id = drawable), contentDescription = "")
    }
}