package com.test.mangofzcotest.presentation.navigation.auth.phoneinput

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
import androidx.compose.runtime.derivedStateOf
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
import com.test.mangofzcotest.presentation.base.state.ScreenState
import com.test.mangofzcotest.presentation.base.state.isError
import com.test.mangofzcotest.presentation.base.state.isLoading
import com.test.mangofzcotest.presentation.base.ui.BlueTextButton
import com.test.mangofzcotest.presentation.base.ui.BlueTextField
import com.test.mangofzcotest.presentation.base.ui.MaskVisualTransformation
import com.test.mangofzcotest.presentation.base.ui.PrimaryCircularProgressIndicator
import com.test.mangofzcotest.presentation.base.ui.StateHandler
import com.test.mangofzcotest.presentation.base.ui.ToastMessage
import com.test.mangofzcotest.presentation.theme.Theme
import com.test.mangofzcotest.presentation.theme.dep


@Composable
fun AuthPhoneInputScreen(
    viewModel: AuthPhoneInputViewModel = hiltViewModel(),
    onNavigate: (phone: String) -> Unit
) {
    val currentState: ScreenState<String> by viewModel.screenState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dep),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PhoneInputContent(
            modifier = Modifier.weight(4f),
            isLoading = currentState.isLoading,
            isError = currentState.isError,
            onSendPhone = viewModel::sendAuthCode,
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
                content = { phone ->
                    LaunchedEffect(phone) {
                        onNavigate(phone)
                    }
                }
            )
        }
    }
}

typealias onSendPhoneCallback = (phone: String) -> Unit

@Composable
fun PhoneInputContent(
    modifier: Modifier = Modifier,
    isError: Boolean,
    isLoading: Boolean,
    onSendPhone: onSendPhoneCallback
) {
    var phone by remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf(Country.RUSSIA) }

    val maskVisualTransformation by remember {
        derivedStateOf {
            MaskVisualTransformation(selectedCountry.phoneMask)
        }
    }

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
            text = stringResource(R.string.enter_your_phone_number),
            style = Theme.typography.titleLarge,
            color = Theme.colors.onBackground,
            modifier = Modifier.padding(bottom = 16.dep)
        )

        BlueTextField(
            value = phone,
            onValueChange = {
                phone = it.filterWithMask(selectedCountry.phoneMask)
            },
            placeholderText = selectedCountry.phoneMask,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dep),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (phone.checkMask(selectedCountry.phoneMask)) {
                        onSendPhone(phone)
                        keyboardController?.hide()
                        focusRequester.clearFocus()
                    }
                }),
            isError = isError,
            visualTransformation = maskVisualTransformation,
            leadingIcon = {
                CountryPicker(
                    selectedCountry = selectedCountry,
                    countryList = Country.entries,
                    onCountryChange = {
                        selectedCountry = it
                        phone = ""
                    }
                )
            }
        )

        BlueTextButton(
            onClick = {
                onSendPhone("${selectedCountry.dialCode}$phone")
                keyboardController?.hide()
                focusRequester.clearFocus()
            },
            enabled = !isLoading && phone.checkMask(selectedCountry.phoneMask),
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.send_code)
        )
    }
}

