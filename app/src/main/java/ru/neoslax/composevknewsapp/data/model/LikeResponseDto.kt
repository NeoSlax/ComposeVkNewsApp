package ru.neoslax.composevknewsapp.data.model

import com.google.gson.annotations.SerializedName

data class LikeResponseDto(
    @SerializedName("response") val response: LikeCountDto
)

data class LikeCountDto(
    @SerializedName("likes") val count: Int
)
