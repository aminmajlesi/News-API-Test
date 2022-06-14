package com.example.newsapp

data class Message(
    val description: String,
    val id: String,
    val image: String,
    val title: String,
    val unread: Boolean
)