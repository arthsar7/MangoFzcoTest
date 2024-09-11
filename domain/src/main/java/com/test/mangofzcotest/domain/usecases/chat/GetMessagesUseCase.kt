package com.test.mangofzcotest.domain.usecases.chat

import com.test.mangofzcotest.domain.entities.Message
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor() {
    operator fun invoke(chatId: Int): Result<List<Message>> {
        return Result.success(
            listOf(
                Message(
                    sender = "Sender 1",
                    content = "Content 1",
                    isMine = false,
                    time = "10:00"
                ),
                Message(
                    sender = "Me",
                    content = "Content 2",
                    isMine = true,
                    time = "10:01"
            )
        ))
    }
}