package com.test.mangofzcotest.presentation.navigation.home.chat

import android.os.Build
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.mangofzcotest.R
import com.test.mangofzcotest.domain.entities.Message
import com.test.mangofzcotest.presentation.base.ui.BlueTextField
import com.test.mangofzcotest.presentation.base.ui.LoadingDialog
import com.test.mangofzcotest.presentation.base.ui.StateHandler
import com.test.mangofzcotest.presentation.base.ui.ToastMessage
import com.test.mangofzcotest.presentation.base.ui.activity
import com.test.mangofzcotest.presentation.theme.Theme
import com.test.mangofzcotest.presentation.theme.dep

@Composable
fun CurrentChatScreen(
    viewModel: CurrentChatViewModel = hiltViewModel(),
) {
    var currentMessage by remember { mutableStateOf("") }
    val state by viewModel.screenState.collectAsState()

    val keyboard = LocalSoftwareKeyboardController.current

    val focusRequester = LocalFocusManager.current
    with(activity) {
        LaunchedEffect(this) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                WindowCompat.setDecorFitsSystemWindows(window, false)
            }
            else {
                @Suppress("DEPRECATION")
                window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }
        }
    }

    StateHandler(
        state = state,
        loadingContent = {
            LoadingDialog()
        },
        errorContent = { error ->
            ToastMessage(error)
        },
        content = { messages ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dep)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dep)
                ) {
                    items(messages) { message ->
                        MessageItem(message = message)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dep),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BlueTextField(
                        value = currentMessage,
                        onValueChange = { currentMessage = it },
                        modifier = Modifier.weight(1f),
                        placeholderText = stringResource(R.string.message)
                    )

                    Spacer(modifier = Modifier.width(8.dep))
                    IconButton(
                        onClick = {
                            if (currentMessage.isNotEmpty()) {
                                viewModel.sendMessage(currentMessage)
                                currentMessage = ""
                                keyboard?.hide()
                                focusRequester.clearFocus()
                            }
                        },
                        colors = Theme.iconButtonColors.baseColors,
                        modifier = Modifier.alpha(if (currentMessage.isNotEmpty()) 1f else 0.5f)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = stringResource( R.string.send)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun MessageItem(message: Message) {
    val backgroundColor = if (message.isMine) Theme.colors.primary else Theme.colors.onPrimaryAlpha
    val alignment = if (message.isMine) Alignment.End else Alignment.Start
    val paddingStartEnd =
        if (message.isMine) Modifier.padding(start = 40.dep) else Modifier.padding(end = 40.dep)

    // Detect arrow shape based on message direction
    val shapeWithArrow = if (message.isMine) {
        RoundedCornerShape(12.dep).copy(bottomEnd = CornerSize(0.dep)) // Right arrow
    } else {
        RoundedCornerShape(12.dep).copy(bottomStart = CornerSize(0.dep)) // Left arrow
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(paddingStartEnd)
            .padding(8.dep),
        horizontalAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = shapeWithArrow
                )
                .clip(shapeWithArrow)
                .padding(12.dep)
        ) {
            Column {
                Text(
                    text = message.sender,
                    style = Theme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.height(4.dep))
                Text(
                    text = message.content,
                    style = Theme.typography.bodyRegular,
                )
            }
        }

        Text(
            text = message.time,
            style = Theme.typography.bodySmall,
            modifier = Modifier
                .align(alignment)
                .padding(top = 4.dep),
            textAlign = if (message.isMine) TextAlign.End else TextAlign.Start
        )
    }
}

