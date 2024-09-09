package com.test.mangofzcotest.presentation.navigation.auth

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.mangofzcotest.presentation.ToastMessage
import com.text.mangofzcotest.core.utils.dep

@Composable
fun AuthCodeInputScreen(
    viewModel: AuthCodeInputViewModel = hiltViewModel(),
    phone: String,
    onRegister: (phone: String) -> Unit,
    onSuccess: (phone: String) -> Unit
) {
    val currentState by viewModel.currentScreenState.collectAsState()
    var code by remember { mutableStateOf("") }
    Column {
        SmsCodeInputScreen(
            currentState = currentState,
            phone = phone,
            code = code,
            onCodeChange = { code = it },
            onSendCode = { phone, code ->
                viewModel.checkAuthCode(phone, code, onRegister, onSuccess)
            },
        )
        when (val state = currentState) {
            is ScreenState.Error -> {
                ToastMessage(state.errorMessage)
            }
            ScreenState.Idle -> {
                /* nothing */
            }
            ScreenState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            ScreenState.Success -> {
                /* nothing */
            }
        }
    }
}
@Composable
fun SmsCodeInputScreen(
    currentState: ScreenState,
    phone: String,
    code: String,
    onCodeChange: (String) -> Unit,
    onSendCode: (phone: String, code: String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dep),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Введите код подтверждения", style = MaterialTheme.typography.headlineMedium)
        TextField(
            value = code,
            onValueChange = {
                onCodeChange(it.filter { char -> char.isDigit() }.take(6))
            },
            placeholder = { Text("Введите код") },
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
            Text("Подтвердить код")
        }
        if (currentState.isLoading) {
            CircularProgressIndicator()
        }
    }
}