package com.test.mangofzcotest.presentation.navigation.home.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.mangofzcotest.R
import com.test.mangofzcotest.domain.entities.Chat
import com.test.mangofzcotest.presentation.base.state.isLoading
import com.test.mangofzcotest.presentation.base.ui.LoadingDialog
import com.test.mangofzcotest.presentation.base.ui.StateHandler
import com.test.mangofzcotest.presentation.base.ui.ToastMessage
import com.test.mangofzcotest.presentation.theme.Theme
import com.test.mangofzcotest.presentation.theme.dep

@Composable
fun ChatListScreen(viewModel: ChatsViewModel = hiltViewModel(), onChatClick: (Chat) -> Unit) {
    val state by viewModel.screenState.collectAsState()
    StateHandler(
        state = state,
        loadingContent = {
            LoadingDialog()
        },
        errorContent = { error ->
            ToastMessage(error)
        },
        content = { chats ->
            ChatList(state.isLoading, chats, onChatClick)
        }
    )
}

@Composable
fun ChatList(isLoading: Boolean, chats: List<Chat>, onChatClick: (Chat) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dep),
    ) {
        if (chats.isNotEmpty() || isLoading) {
            LazyColumn {
                items(chats) { chat ->
                    ChatListItem(chat = chat, onClick = onChatClick)
                }
            }
        } else {
            Text(
                text = stringResource(R.string.no_chats),
                modifier = Modifier.padding(16.dep)
            )
        }
    }
}

@Composable
fun ChatListItem(chat: Chat, onClick: (Chat) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(chat) }
            .padding(vertical = 12.dep, horizontal = 16.dep)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .size(48.dep)
                    .clip(CircleShape)
                    .background(Theme.colors.onPrimaryAlpha)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .clip(CircleShape)
                )
                // Load avatar if needed
            }

            Spacer(modifier = Modifier.width(12.dep))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = chat.name,
                    style = Theme.typography.bodyRegular,
                    maxLines = 1
                )
                Text(
                    text = chat.lastMessage,
                    style = Theme.typography.bodySmall,
                    maxLines = 1,
                    modifier = Modifier.padding(top = 4.dep)
                )
            }

            Spacer(modifier = Modifier.width(12.dep))

            Text(
                text = chat.lastMessageTime,
                style = Theme.typography.bodySmall,
            )
        }

        HorizontalDivider(
            color = Theme.colors.onBackground,
            thickness = 0.5.dep,
            modifier = Modifier.padding(vertical = 8.dep)
        )
    }
}
