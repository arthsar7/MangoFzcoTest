package com.test.mangofzcotest.presentation.navigation.auth.codeinput

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.mangofzcotest.presentation.StateHandler
import com.test.mangofzcotest.presentation.ToastMessage
import com.test.mangofzcotest.presentation.navigation.screen.ScreenState
import com.test.mangofzcotest.presentation.navigation.screen.isLoading
import com.test.mangofzcotest.presentation.theme.Theme
import com.test.mangofzcotest.presentation.theme.dep

@Composable
fun AuthCodeInputScreen(
    viewModel: AuthCodeInputViewModel = hiltViewModel(),
    onRegister: (phone: String) -> Unit,
    onSuccess: (phone: String) -> Unit
) {
    val currentState by viewModel.screenState.collectAsState()
    Column {
        SmsCodeInputScreen(
            currentState = currentState,
            phone = viewModel.phone,
            onSendCode = viewModel::checkAuthCode,
        )
        StateHandler(
            state = currentState,
            loadingContent = {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            },
            errorContent = {
                ToastMessage(it.errorMessage)
            },
            content = { successState ->
                val codeResult = successState.data
                LaunchedEffect(codeResult) {
                    when (codeResult) {
                        is CodeResult.UserExists -> onSuccess(codeResult.phone)
                        is CodeResult.UserNotExists -> onRegister(codeResult.phone)
                        else -> {}
                    }
                }
            }
        )
    }
}

@Composable
fun SmsCodeInputScreen(
    currentState: ScreenState<Any?>,
    phone: String,
    onSendCode: (phone: String, code: String) -> Unit,
) {
    var code by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dep),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Enter SMS code", style = Theme.typography.titleAuthScreen)
        TextField(
            value = code,
            onValueChange = {
                code = it.filter { char -> char.isDigit() }.take(6)
            },
            placeholder = { Text("SMS code") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dep))
        Button(
            onClick = { onSendCode(phone, code) },
            enabled = !currentState.isLoading && code.length == 6,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send code")
        }
        if (currentState.isLoading) {
            CircularProgressIndicator()
        }
    }
}