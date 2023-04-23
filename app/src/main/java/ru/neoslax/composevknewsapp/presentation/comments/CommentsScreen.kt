package ru.neoslax.composevknewsapp.presentation.comments

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.neoslax.composevknewsapp.R
import ru.neoslax.composevknewsapp.domain.model.FeedItem
import ru.neoslax.composevknewsapp.domain.model.PostComment
import ru.neoslax.composevknewsapp.ui.theme.VkMainColor

@Composable
fun CommentsScreen(
    feedItem: FeedItem,
    onBackButtonClick: () -> Unit
) {
    val application = LocalContext.current.applicationContext as Application

    val viewModel: CommentsViewModel = viewModel(
        factory = CommentsViewModelFactory(
            feedItem,
            application
        )
    )
    val screenState = viewModel.screenState.observeAsState(CommentsScreenState.Initial)
    val currentState = screenState.value

    when (currentState) {
        is CommentsScreenState.Initial -> {}
        is CommentsScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = VkMainColor)
            }
        }

        is CommentsScreenState.Comments -> {
            Scaffold(topBar = {
                TopAppBar(title = {
                    Text(text = stringResource(R.string.comments_title))
                }, navigationIcon = {
                    IconButton(onClick = onBackButtonClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = null
                        )
                    }
                })
            }) { paddingValues ->
                LazyColumn(
                    modifier = Modifier.padding(paddingValues),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 72.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = currentState.comments, key = { it.id }) {
                        CommentItem(content = it)
                    }
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
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            model = content.userPhotoUrl,
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column {
            Text(
                text = content.authorName,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = content.authorComment, color = MaterialTheme.colors.onPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = content.commentTime, color = MaterialTheme.colors.onSecondary)
        }
    }
}