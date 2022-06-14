package com.example.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.models.Message

@Database(
    entities = [Message::class],
    version = 1
)

abstract class MessageDatabase : RoomDatabase(){

    abstract fun getMessageDao(): MessageDao

    companion object {
        @Volatile
        private var instance: MessageDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MessageDatabase::class.java,
                "message_db.db"
            ).build()
    }

}