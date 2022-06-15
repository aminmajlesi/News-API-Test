package com.example.newsapp.repository

import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.db.MessageDatabase

class NewsRepository(
    val db: MessageDatabase
) {
    suspend fun getBreakingNews() = RetrofitInstance.api.getBreakingNews()
}