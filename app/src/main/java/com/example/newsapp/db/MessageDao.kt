package com.example.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.models.Message

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(message : Message): Long

    @Query("SELECT * FROM message_table")
    fun getAllMessage(): LiveData<List<Message>>

    @Delete
    suspend fun deleteMessage(message: Message)

}