package com.example.newsapp.api

import com.example.newsapp.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsAPI {

    @GET("v3/top-headlines")
    suspend fun getBreakingNews(

    ): Response<NewsResponse>
}