package com.example.newsapp.repository

import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.db.MessageDatabase
import com.example.newsapp.models.Message

class NewsRepository(
    val db: MessageDatabase
) {

    val local = db

    suspend fun getBreakingNews() = RetrofitInstance.api.getBreakingNews()

    suspend fun upsert(message: Message) = db.getMessageDao().upsert(message)

    fun getSavedNews() = db.getMessageDao().getAllMessage()

    suspend fun deleteMessage(message: Message) = db.getMessageDao().deleteMessage(message)
}