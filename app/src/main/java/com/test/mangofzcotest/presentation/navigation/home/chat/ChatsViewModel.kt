package com.test.mangofzcotest.presentation.navigation.home.chat

import androidx.lifecycle.viewModelScope
import com.test.mangofzcotest.domain.entities.Chat
import com.test.mangofzcotest.domain.usecases.chat.GetChatsUseCase
import com.test.mangofzcotest.presentation.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase
) : BaseViewModel<List<Chat>>() {

    init {
        loadChats()
    }

    private fun loadChats() {
        viewModelScope.launch {
            emitLoading(emptyList())
            delay(2000)
            getChatsUseCase().onSuccess(::emitSuccess).handleFailure()
        }
    }
}