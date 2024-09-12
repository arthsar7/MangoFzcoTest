package com.test.mangofzcotest.presentation.navigation.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.mangofzcotest.R
import com.test.mangofzcotest.presentation.base.state.isLoading
import com.test.mangofzcotest.presentation.base.ui.BlueTextButton
import com.test.mangofzcotest.presentation.base.ui.BlueTextField
import com.test.mangofzcotest.presentation.base.ui.PrimaryCircularProgressIndicator
import com.test.mangofzcotest.presentation.base.ui.StateHandler
import com.test.mangofzcotest.presentation.base.ui.ToastMessage
import com.test.mangofzcotest.presentation.theme.Theme
import com.test.mangofzcotest.presentation.theme.dep

@Composable
fun AuthRegisterScreen(
    viewModel: AuthRegisterViewModel = hiltViewModel(),
    onSuccess: (phone: String) -> Unit
) {
    val currentState by viewModel.screenState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dep),
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        RegisterScreen(
            modifier = Modifier
                .weight(4f),
            isLoading = currentState.isLoading,
            phone = viewModel.phone,
            onRegister = viewModel::register,
        )
        Column(modifier = Modifier.weight(1f)) {
            StateHandler(
                state = currentState,
                loadingContent = {
                    PrimaryCircularProgressIndicator()
                },
                errorContent = { error ->
                    ToastMessage(error)
                },
                content = { data ->
                    LaunchedEffect(data) {
                        onSuccess(data)
                    }
                }
            )
        }
    }
}

typealias OnRegisterCallback = (phone: String, name: String, username: String) -> Unit

@Composable
fun RegisterScreen(
    modifier: Modifier,
    isLoading: Boolean,
    phone: String,
    onRegister: OnRegisterCallback,
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    val keyboard = LocalSoftwareKeyboardController.current

    val focusRequester = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dep),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.register),
            style = Theme.typography.titleLarge
        )

        Text(text = phone, style = Theme.typography.bodyRegular)

        Spacer(modifier = Modifier.height(16.dep))

        BlueTextField(
            value = name,
            onValueChange = {
                if (!isLoading) name = it
            },
            placeholderText = stringResource(id = R.string.name),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                autoCorrectEnabled = false
            ),
        )

        Spacer(modifier = Modifier.height(16.dep))

        BlueTextField(
            value = username,
            onValueChange = {
                if (!isLoading) {
                    username = UsernameRequirements.validateInput(it)
                }
            },
            placeholderText = stringResource(R.string.username),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Ascii,
                autoCorrectEnabled = false
            ),
        )

        Spacer(modifier = Modifier.height(16.dep))

        BlueTextButton(
            onClick = {
                onRegister(phone, name, username)
                keyboard?.hide()
                focusRequester.clearFocus()
            },
            enabled = !isLoading && name.isNotEmpty() && UsernameRequirements.isValid(username),
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.register)
        )
    }
}