package com.test.mangofzcotest.presentation.navigation.home.profile.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.test.mangofzcotest.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatBirthday(birthday: String): String {
    val parsedDate = LocalDate.parse(birthday)
    val pattern = "d MMMM yyyy"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return parsedDate.format(formatter)
}

@Composable
fun getZodiacSign(birthday: String): String {
    val month = birthday.substring(5, 7).toInt()
    val day = birthday.substring(8, 10).toInt()

    return when {
        month == 1 && day >= 20 || (month == 2 && day <= 18) -> stringResource(R.string.aquarius)
        month == 2 || (month == 3 && day <= 20) -> stringResource(R.string.pisces)
        month == 3 || (month == 4 && day <= 19) -> stringResource(R.string.aries)
        month == 4 || (month == 5 && day <= 20) -> stringResource(R.string.taurus)
        month == 5 || (month == 6 && day <= 20) -> stringResource(R.string.gemini)
        month == 6 || (month == 7 && day <= 22) -> stringResource(R.string.cancer)
        month == 7 || (month == 8 && day <= 22) -> stringResource(R.string.leo)
        month == 8 || (month == 9 && day <= 22) -> stringResource(R.string.virgo)
        month == 9 || (month == 10 && day <= 22) -> stringResource(R.string.libra)
        month == 10 || (month == 11 && day <= 21) -> stringResource(R.string.scorpio)
        month == 11 || (month == 12 && day <= 21) -> stringResource(R.string.sagittarius)
        else -> stringResource(R.string.capricorn)
    }
}