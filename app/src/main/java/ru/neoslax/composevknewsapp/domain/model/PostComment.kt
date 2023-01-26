package ru.neoslax.composevknewsapp.domain.model

import ru.neoslax.composevknewsapp.R

data class PostComment(
    val id: Int,
    val authorName: String = "Alex N.",
    val authorComment: String = "Some comment",
    val userPhotoId: Int = R.drawable.ic_comment,
    val commentTime: String = "11:00"
)
