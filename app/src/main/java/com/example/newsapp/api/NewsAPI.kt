package com.example.newsapp.api

import com.example.newsapp.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsAPI {

    @GET("v3/729e846c-80db-4c52-8765-9a762078bc82")
    suspend fun getBreakingNews(

    ): Response<NewsResponse>
}