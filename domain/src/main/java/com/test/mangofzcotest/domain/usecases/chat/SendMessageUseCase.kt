package com.test.mangofzcotest.domain.usecases.chat

import com.test.mangofzcotest.domain.entities.Message
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class SendMessageUseCase @Inject constructor() {
    operator fun invoke(chatId: Int, message: String): Result<Message> {
        return Result.success(
            Message(
                sender = "Me",
                content = message,
                isMine = true,
                time = currentTime,
            )
        )
    }
}
val currentTime get(): String {
    val currentTime = LocalTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return currentTime.format(formatter)
}