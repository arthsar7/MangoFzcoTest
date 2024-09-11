package com.test.mangofzcotest.domain.entities

data class Message(
    val sender: String,
    val content: String,
    val time: String,
    val isMine: Boolean
)