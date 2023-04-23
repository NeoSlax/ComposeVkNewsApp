package ru.neoslax.composevknewsapp.domain.model

data class PostComment(
    val id: Long,
    val authorName: String,
    val authorComment: String,
    val userPhotoUrl: String,
    val commentTime: String
)
