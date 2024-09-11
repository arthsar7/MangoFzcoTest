package com.test.mangofzcotest.presentation.navigation.auth.register

data object UsernameRequirements {

    private val usernameRegex: Regex = "^[a-zA-Z0-9_-]*$".toRegex()
    private const val MAX_LIMIT_LENGTH: Int = 30
    private const val MIN_LIMIT_LENGTH: Int = 5

    fun isValid(username: String): Boolean {
        return usernameRegex.matches(username) &&
                username.length <= MAX_LIMIT_LENGTH &&
                username.length >= MIN_LIMIT_LENGTH
    }

    fun validateInput(username: String): String {
        return username.filter { usernameRegex.matches(it.toString()) }.take(MAX_LIMIT_LENGTH)
    }
}