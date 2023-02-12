package ru.neoslax.composevknewsapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.neoslax.composevknewsapp.CommentsViewModelFactory
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.domain.model.PostComment
import ru.neoslax.composevknewsapp.ui.theme.ComposeVkNewsAppTheme

@Composable
fun CommentsScreen(
    feedItem: FeedItem,
    onBackButtonClick: () -> Unit
) {
    val viewModel: CommentsViewModel = viewModel(factory = CommentsViewModelFactory(feedItem))
    val screenState = viewModel.screenState.observeAsState(CommentsScreenState.Initial)
    val currentState = screenState.value

   if (currentState is CommentsScreenState.Comments) {

        Scaffold(topBar = {
            TopAppBar(title = {
                Text(text = "Comments for postId: ${currentState.feedItem.id}")
            }, navigationIcon = {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = null
                    )
                }
            })
        }) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues), contentPadding = PaddingValues(
                    top = 16.dp, start = 8.dp, end = 8.dp, bottom = 72.dp
                )
            ) {
                items(items = currentState.comments, key = { it.id }) {
                    CommentItem(content = it)
                }
            }
        }
    }
}

@Composable
fun CommentItem(
    content: PostComment
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp, vertical = 4.dp
            )
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = content.userPhotoId),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(8.dp))
        Column {
            Text(
                text = content.authorName + " CommentId: " + content.id,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = content.authorComment, color = MaterialTheme.colors.onPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = content.commentTime, color = MaterialTheme.colors.onSecondary)
        }
    }
}

@Preview
@Composable
private fun CommentItemPreviewLight() {
    ComposeVkNewsAppTheme(darkTheme = false) {
        CommentItem(
            content = PostComment(0)
        )
    }
}

@Preview
@Composable
private fun CommentItemPreviewNight() {
    ComposeVkNewsAppTheme(darkTheme = true) {
        CommentItem(
            content = PostComment(0)
        )
    }
}