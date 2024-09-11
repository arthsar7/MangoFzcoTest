package com.test.mangofzcotest.domain.usecases.chat

import com.test.mangofzcotest.domain.entities.Chat
import javax.inject.Inject

class GetChatsUseCase @Inject constructor() {

    operator fun invoke(): Result<List<Chat>> = Result.success(listOf(
        Chat(
            id = 1,
            name = "Chat 1",
            lastMessage = "Last message 1",
            lastMessageTime = "10:00"
        ),
        Chat(
            id = 2,
            name = "Chat 2",
            lastMessage = "Last message 2",
            lastMessageTime = "10:01"
        )
    ))
}
