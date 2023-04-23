package ru.neoslax.composevknewsapp.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.neoslax.composevknewsapp.data.model.DeletePostResponseDto
import ru.neoslax.composevknewsapp.data.model.LikeResponseDto
import ru.neoslax.composevknewsapp.data.model.VKNewsFeedResponseDto

interface ApiService {


    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadNewsFeed(
        @Query("access_token") token: String
    ): VKNewsFeedResponseDto

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadNewsFeed(
        @Query("access_token") token: String,
        @Query("start_from") nextFrom: String
    ): VKNewsFeedResponseDto


    @GET("likes.add?v=5.131&type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikeResponseDto

    @GET("likes.delete?v=5.131&type=post")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikeResponseDto

    @GET("newsfeed.ignoreItem?v=5.131&type=wall")
    suspend fun deleteNewsItem(
        @Query("access_token") token: String,
        @Query("owner_id") owner_id: Long,
        @Query("item_id") item_id: Long
    ): DeletePostResponseDto
}