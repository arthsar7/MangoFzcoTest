package com.test.mangofzcotest.presentation.navigation.auth.phoneinput

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.test.mangofzcotest.presentation.navigation.screen.ScreenState
import com.test.mangofzcotest.presentation.navigation.screen.isLoading
import com.test.mangofzcotest.presentation.theme.Theme
import com.test.mangofzcotest.presentation.theme.dep


@Composable
fun AuthPhoneInputScreen(
    viewModel: AuthPhoneInputViewModel = hiltViewModel(),
    onNavigate: (phone: String) -> Unit
) {
    val currentState: ScreenState<String> by viewModel.screenState.collectAsState()
    Column {
        PhoneInputContent(
            currentState = currentState,
            onSendPhone = viewModel::sendAuthCode,
        )
        StateHandler(
            state = currentState,
            loadingContent = {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            },
            errorContent ={
                ToastMessage(it.errorMessage)
            },
            content = {
                val phone = it.data
                LaunchedEffect(phone) {
                    if (phone != null) {
                        onNavigate(phone)
                    }
                }
            }
        )
    }

}

@Composable
fun CountryPicker(
    selectedCountry: Country,
    countryList: List<Country>,
    onCountryChange: (Country) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text(text = "${selectedCountry.flag} ${selectedCountry.dialCode}")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            countryList.forEach { country ->
                DropdownMenuItem(onClick = {
                    onCountryChange(country)
                    expanded = false
                }, text = {
                    Text(text = "${country.flag} ${country.dialCode}")
                })
            }
        }
    }
}

// Пример enum для стран с кодами и флагами
enum class Country(val flag: String, val dialCode: String) {
    RUSSIA("🇷🇺", "+7"),
    USA("🇺🇸", "+1"),
    UK("🇬🇧", "+44")
}

// Пример маски для номера телефона
fun applyPhoneMask(input: String, dialCode: String): String {
    // Пример маски с кодом страны
    return if (input.startsWith(dialCode)) {
        input
    }
    else {
        dialCode + input.filter { it.isDigit() }
    }
}

@Composable
fun PhoneInputContent(
    currentState: ScreenState<Any?>,
    onSendPhone: (String) -> Unit,
) {
    var phone by remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf(Country.RUSSIA) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dep),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.enter_your_phone_number), style = Theme.typography.titleAuthScreen)
        CountryPicker(
            selectedCountry = selectedCountry,
            countryList = Country.entries,
            onCountryChange = {
                selectedCountry = it
            }
        )

        TextField(
            value = phone,
            onValueChange = {
                phone = applyPhoneMask(it, selectedCountry.dialCode)
            },
            placeholder = { Text(stringResource(R.string.phone_number)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dep))
        Button(
            onClick = { onSendPhone(phone) },
            enabled = !currentState.isLoading && phone.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.send_code))
        }
    }
}
