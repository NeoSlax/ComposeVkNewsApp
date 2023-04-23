package ru.neoslax.composevknewsapp.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiFactory {

    private const val BASE_URL = "https://api.vk.com/method/"

    private val loggerClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(loggerClient)
        .baseUrl(BASE_URL)
        .build()

    val api: ApiService = retrofit.create()
}