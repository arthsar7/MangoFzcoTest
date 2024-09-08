package com.test.mangofzcotest.presentation

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigate: () -> Unit
) {
    val authScreenState: AuthScreenState by viewModel.authScreenState.collectAsState()

    ErrorHandler(authScreenState)

    when (val state = authScreenState) {
        is AuthScreenState.PhoneInput -> PhoneInputScreen(
            currentState = state,
            onSendPhone = viewModel::sendAuthCode,
        )

        is AuthScreenState.SmsCodeInput -> SmsCodeInputScreen(
            currentState = state,
            onSendCode = viewModel::checkAuthCode,
        )

        is AuthScreenState.Register -> RegisterScreen(
            currentState = state,
            onRegister = viewModel::register,
        )

        is AuthScreenState.Success -> {
            LaunchedEffect(Unit) {
                onNavigate()
            }
        }
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
fun PhoneInputScreen(
    currentState: AuthScreenState.PhoneInput,
    onSendPhone: (String) -> Unit,
) {
    var phone by remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf(Country.RUSSIA) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Введите номер телефона", style = MaterialTheme.typography.headlineMedium)
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
            placeholder = { Text("Введите телефон") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSendPhone(phone) },
            enabled = !currentState.isLoading && phone.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Отправить код")
        }
        if (currentState.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun SmsCodeInputScreen(
    currentState: AuthScreenState.SmsCodeInput,
    onSendCode: (phone: String, code: String) -> Unit,
) {
    var code by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Введите код подтверждения", style = MaterialTheme.typography.headlineMedium)
        TextField(
            value = code,
            onValueChange = {
                code = it.filter { char -> char.isDigit() }.take(6)
            },
            placeholder = { Text("Введите код") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSendCode(currentState.phoneNumber, code) },
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

@Composable
fun RegisterScreen(
    currentState: AuthScreenState.Register,
    onRegister: (phone: String, name: String, username: String) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    val usernameRegex = "^[a-zA-Z0-9_-]*$".toRegex()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Регистрация", style = MaterialTheme.typography.headlineMedium)

        // Номер телефона без возможности редактирования
        Text(text = currentState.phoneNumber, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Введите имя") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                autoCorrectEnabled = false
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("Введите username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Ascii,
                autoCorrectEnabled = false
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (usernameRegex.matches(username) && name.isNotEmpty() && username.isNotEmpty()) {
                    onRegister(currentState.phoneNumber, name, username)
                }
            },
            enabled = name.isNotEmpty() && username.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Зарегистрироваться")
        }

        if (currentState.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun ErrorHandler(
    currentState: AuthScreenState,
    context: Context = LocalContext.current
) {
    LaunchedEffect(currentState.isError) {
        if (currentState.isError) {
            Toast.makeText(context, currentState.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}
