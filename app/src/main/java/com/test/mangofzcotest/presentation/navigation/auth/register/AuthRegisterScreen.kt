package com.test.mangofzcotest.presentation.navigation.auth.register

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.mangofzcotest.R
import com.test.mangofzcotest.presentation.StateHandler
import com.test.mangofzcotest.presentation.ToastMessage
import com.test.mangofzcotest.presentation.theme.Theme
import com.test.mangofzcotest.presentation.theme.dep

@Composable
fun AuthRegisterScreen(
    viewModel: AuthRegisterViewModel = hiltViewModel(),
    onSuccess: (phone: String) -> Unit
) {
    val currentState by viewModel.screenState.collectAsState()
    Column {
        RegisterScreen(
            phone = viewModel.phone,
            onRegister = viewModel::register
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
            content = {
                val data = it.data
                LaunchedEffect(data) {
                    if (data != null) {
                        onSuccess(data)
                    }
                }
            }
        )
    }
}

@Composable
fun RegisterScreen(
    phone: String,
    onRegister: (phone: String, name: String, username: String) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    val usernameRegex = "^[a-zA-Z0-9_-]*$".toRegex()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dep),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.register), style = Theme.typography.titleAuthScreen)

        // Номер телефона без возможности редактирования
        Text(text = phone, style = Theme.typography.regularAuthScreen)

        Spacer(modifier = Modifier.height(16.dep))

        TextField(
            value = name,
            onValueChange =  {
                name = it
            },
            placeholder = { Text(stringResource(R.string.name)) },
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
            onValueChange = {
                username = it
            },
            placeholder = { Text(stringResource(R.string.username)) },
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
            Text(stringResource(R.string.register))
        }
    }
}