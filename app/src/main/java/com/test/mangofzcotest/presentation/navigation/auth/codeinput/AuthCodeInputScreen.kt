package com.test.mangofzcotest.presentation.navigation.auth.codeinput

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
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
import com.test.mangofzcotest.presentation.base.state.isError
import com.test.mangofzcotest.presentation.base.state.isLoading
import com.test.mangofzcotest.presentation.base.ui.BlueTextButton
import com.test.mangofzcotest.presentation.base.ui.BlueTextField
import com.test.mangofzcotest.presentation.base.ui.PrimaryCircularProgressIndicator
import com.test.mangofzcotest.presentation.base.ui.StateHandler
import com.test.mangofzcotest.presentation.base.ui.ToastMessage
import com.test.mangofzcotest.presentation.theme.Theme
import com.test.mangofzcotest.presentation.theme.dep

@Composable
fun AuthCodeInputScreen(
    viewModel: AuthCodeInputViewModel = hiltViewModel(),
    onRegister: (phone: String) -> Unit,
    onSuccess: (phone: String) -> Unit
) {
    val currentState by viewModel.screenState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dep),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SmsCodeInputScreen(
            modifier = Modifier
                .weight(4f),
            isLoading = currentState.isLoading,
            phone = viewModel.phone,
            onSendCode = viewModel::checkAuthCode,
            isError = currentState.isError
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
                content = { codeResult ->
                    LaunchedEffect(codeResult) {
                        when (codeResult) {
                            is CodeResult.UserExists -> onSuccess(codeResult.phone)
                            is CodeResult.UserNotExists -> onRegister(codeResult.phone)
                        }
                    }
                }
            )
        }
    }
}

typealias onSendCodeCallback = (phone: String, code: String) -> Unit

@Composable
fun SmsCodeInputScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isError: Boolean,
    phone: String,
    onSendCode: onSendCodeCallback,
) {
    var code by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dep),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.enter_sms_code),
            style = Theme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dep)
        )

        BlueTextField(
            value = code,
            onValueChange = {
                code = it.filter(Char::isDigit).take(6)
            },
            placeholderText = stringResource(id = R.string.sms_code),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dep),
            keyboardActions = KeyboardActions(onDone = {
                if (!isLoading && code.length == 6) {
                    onSendCode(phone, code)
                    keyboardController?.hide()
                    focusRequester.clearFocus()
                }
            }),
            isError = isError
        )

        BlueTextButton(
            onClick = {
                onSendCode(phone, code)
                keyboardController?.hide()
                focusRequester.clearFocus()
            },
            enabled = !isLoading && code.length == 6,
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.send_code)
        )
    }
}
