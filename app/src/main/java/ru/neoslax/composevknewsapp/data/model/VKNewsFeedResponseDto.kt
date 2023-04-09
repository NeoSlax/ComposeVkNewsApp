package ru.neoslax.composevknewsapp.data.model

import com.google.gson.annotations.SerializedName

data class VKNewsFeedResponseDto(
    @SerializedName("response")
    val response: VKResponseDto
)

class VKResponseDto(
    @SerializedName("items")
    val items: List<PostItemDto>,
    @SerializedName("groups")
    val groups: List<GroupDto>,
    @SerializedName("next_from")
    val nextFrom: String?
)

data class PostItemDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("source_id")
    val communityId: Long,
    @SerializedName("is_favourite")
    val isFavourite: Boolean,
    @SerializedName("text")
    val text: String,
    @SerializedName("date")
    val date: Long,
    @SerializedName("likes")
    val likes: LikesDto,
    @SerializedName("comments")
    val comments: CommentsDto,
    @SerializedName("reposts")
    val reposts: RepostDto,
    @SerializedName("views")
    val views: ViewsDto,

    @SerializedName("attachments")
    val attachmentsDto: List<AttachmentsDto>
)

data class AttachmentsDto(
    @SerializedName("photo")
    val photoUrls: PhotoDto
)

data class PhotoDto(
    @SerializedName("sizes")
    val photos: List<PhotoUrlDto>
)

data class CommentsDto(
    @SerializedName("count")
    val count: Int
)


data class LikesDto(
    @SerializedName("count")
    val count: Int,
    @SerializedName("user_likes")
    val isLiked: Int
)


data class ViewsDto(
    @SerializedName("count")
    val count: Int
)


data class RepostDto(
    @SerializedName("count")
    val count: Int
)

data class PhotoUrlDto(
    @SerializedName("url")
    val url: String
)

data class GroupDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo_200")
    val groupImageUrl: String
)