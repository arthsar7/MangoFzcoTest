package com.test.mangofzcotest.presentation.auth


sealed class AuthScreenState(
    open val isLoading: Boolean = false,
    open val isError: Boolean = false,
    open val errorMessage: String? = null,
) {
    data class PhoneInput(
        override val isLoading: Boolean = false,
        override val isError: Boolean = false,
        override val errorMessage: String? = null,
    ) : AuthScreenState(isLoading, isError, errorMessage)

    data class SmsCodeInput(
        override val isLoading: Boolean = false,
        override val isError: Boolean = false,
        override val errorMessage: String? = null,
        val phoneNumber: String,
    ) : AuthScreenState(isLoading, isError, errorMessage)

    data class Register(
        override val isLoading: Boolean = false,
        override val isError: Boolean = false,
        override val errorMessage: String? = null,
        val phoneNumber: String,
    ) : AuthScreenState(isLoading, isError, errorMessage)

    data object Success : AuthScreenState()
}