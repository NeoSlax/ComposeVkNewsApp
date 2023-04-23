package ru.neoslax.composevknewsapp.data.model

import com.google.gson.annotations.SerializedName

data class DeletePostResponseDto(
    @SerializedName("response") val responseCode: Int
)