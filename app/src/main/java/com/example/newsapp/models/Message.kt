package com.example.newsapp.models

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "message_table" ,indices = [Index(value = ["id"], unique = true)])
data class Message(
    @PrimaryKey(autoGenerate = true)
    var idNum: Int? = null,
    val description: String,
    val id: String,
    val image: String?,
    val title: String,
    val unread: Boolean,
    val isBookmarked: Boolean
) : Serializable