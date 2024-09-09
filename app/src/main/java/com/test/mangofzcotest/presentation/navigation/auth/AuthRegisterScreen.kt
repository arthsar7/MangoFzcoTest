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
fun AuthRegisterScreen(
    phone: String,
    viewModel: AuthRegisterViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    val currentState by viewModel.currentScreenState.collectAsState()
    Column {
        RegisterScreen(
            phone = phone,
            name = name,
            username = username,
            onChangeName = { name = it },
            onChangeUsername = { username = it },
            onRegister = viewModel::register
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
                onSuccess()
            }
        }
    }
}

@Composable
fun RegisterScreen(
    phone: String,
    name: String,
    username: String,
    onChangeName: (String) -> Unit,
    onChangeUsername: (String) -> Unit,
    onRegister: (phone: String, name: String, username: String) -> Unit,
) {
    val usernameRegex = "^[a-zA-Z0-9_-]*$".toRegex()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dep),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Регистрация", style = MaterialTheme.typography.headlineMedium)

        // Номер телефона без возможности редактирования
        Text(text = phone, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dep))

        TextField(
            value = name,
            onValueChange =  onChangeName,
            placeholder = { Text("Введите имя") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                autoCorrectEnabled = false
            ),
        )

        Spacer(modifier = Modifier.height(16.dep))

        TextField(
            value = username,
            onValueChange = onChangeUsername,
            placeholder = { Text("Введите username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Ascii,
                autoCorrectEnabled = false
            ),
        )

        Spacer(modifier = Modifier.height(16.dep))

        Button(
            onClick = {
                if (usernameRegex.matches(username) && name.isNotEmpty() && username.isNotEmpty()) {
                    onRegister(phone, name, username)
                }
            },
            enabled = name.isNotEmpty() && username.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Зарегистрироваться")
        }
    }
}