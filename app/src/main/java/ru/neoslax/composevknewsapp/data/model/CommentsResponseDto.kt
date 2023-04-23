package ru.neoslax.composevknewsapp.data.model

import com.google.gson.annotations.SerializedName

data class CommentsResponseDto(
    @SerializedName("response") val response: CommentsContentDto
)

data class ProfileDto(
    @SerializedName("id") val id: Long,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("photo_100") val avatarUrl: String
)

data class CommentDto(
    @SerializedName("id") val id: Long,
    @SerializedName("from_id") val authorId: Long,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: Long
)

data class CommentsContentDto(
    @SerializedName("profiles") val profiles: List<ProfileDto>,
    @SerializedName("items") val comments: List<CommentDto>
)
