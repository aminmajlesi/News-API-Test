package com.example.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.models.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(message : Message): Long

    @Query("SELECT * FROM message_table")
    fun getAllMessage(): LiveData<List<Message>>

    @Delete
    suspend fun deleteMessage(message: Message)

    @Query("SELECT * FROM message_table ORDER BY id ASC")
    fun readMessage(): Flow<List<Message>>

    @Query("Update message_table SET isBookmarked = :isBookmarked where id = :id")
    suspend fun updateBookMarked( isBookmarked : Boolean ,  id : String)

    @Query("SELECT * FROM message_table where isBookmarked = 1")
    fun selectBookMarked(): LiveData<List<Message>>

}