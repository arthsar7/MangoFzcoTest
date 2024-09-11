package com.test.mangofzcotest.presentation.navigation.auth.phoneinput

enum class Country(val flag: String, val dialCode: String, val phoneMask: String) {
    RUSSIA("🇷🇺", "+7", "(___) ___-__-__"),
    USA("🇺🇸", "+1", "(___) ___-____"),
    UK("🇬🇧", "+44", "(___) ___-____"),
}