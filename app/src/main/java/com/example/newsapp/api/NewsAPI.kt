package com.example.newsapp.api

import com.example.newsapp.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v3/top-headlines")
    suspend fun getBreakingNews(

    ): Response<NewsResponse>
}