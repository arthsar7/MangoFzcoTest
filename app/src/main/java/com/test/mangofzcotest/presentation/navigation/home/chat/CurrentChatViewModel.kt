package com.test.mangofzcotest.presentation.navigation.home.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.domain.entities.Message
import com.test.mangofzcotest.domain.usecases.chat.GetMessagesUseCase
import com.test.mangofzcotest.domain.usecases.chat.SendMessageUseCase
import com.test.mangofzcotest.presentation.base.viewmodel.BaseViewModel
import com.test.mangofzcotest.presentation.navigation.screen.Screen.HomeGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentChatViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<List<Message>>() {

    private val chatId = HomeGraph.CurrentChat.getChatId(savedStateHandle)

    init {
        loadMessages()
    }

    private fun loadMessages() {
        viewModelScope.launch {
            emitLoading(emptyList())
            delay(2000)
            getMessagesUseCase(chatId).onSuccess(::emitSuccess).handleFailure()
        }
    }

    fun sendMessage(currentMessage: String) {
        viewModelScope.launch {
            delay(300)
            sendMessageUseCase(chatId, currentMessage).onSuccess { message ->
                val currentMessages = currentState.data
                currentMessages?.let {
                    emitSuccess(currentMessages + message)
                }
            }.handleFailure()
        }
    }
}