package com.example.newsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "message_table"
)
data class Message(
    @PrimaryKey(autoGenerate = true)
    var idNum: Int? = null,
    val description: String,
    val id: String,
    val image: String,
    val title: String,
    val unread: Boolean
)