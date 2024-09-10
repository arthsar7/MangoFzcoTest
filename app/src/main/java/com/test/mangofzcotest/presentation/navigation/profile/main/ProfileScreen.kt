package com.test.mangofzcotest.presentation.navigation.profile.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.test.mangofzcotest.R
import com.test.mangofzcotest.domain.entities.UserProfileData
import com.test.mangofzcotest.presentation.LoadingScreen
import com.test.mangofzcotest.presentation.StateHandler
import com.test.mangofzcotest.presentation.ToastMessage
import com.test.mangofzcotest.presentation.navigation.profile.ProfileViewModel
import com.test.mangofzcotest.presentation.theme.Theme
import com.test.mangofzcotest.presentation.theme.dep

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onEditClick: () -> Unit
) {
    val state by viewModel.screenState.collectAsState()

    StateHandler(
        state = state,
        loadingContent = { LoadingScreen() },
        errorContent = {
            ToastMessage(it.errorMessage)
            it.data?.let { profileData ->
                ProfileContent(profileData, onEditClick)
            }
        },
        content = {
            val data = it.data
            data?.let { profileData ->
                ProfileContent(profileData, onEditClick)
            }
        }
    )
}

@Composable
fun ProfileContent(
    userProfileData: UserProfileData,
    onEditClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dep),
        verticalArrangement = Arrangement.spacedBy(16.dep)
    ) {
        // User Avatar
        AsyncImage(
            model = userProfileData.bigAvatar,
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(120.dep)
                .clip(CircleShape)
                .border(2.dep, Color.Gray, CircleShape),
            contentScale = ContentScale.Crop
        )

        // Username
        Text(text = userProfileData.username, style = Theme.typography.titleAuthScreen)

        // Phone
        Text(
            text = "${stringResource(R.string.phone_example)}${userProfileData.phone}",
            style = Theme.typography.regularAuthScreen
        )

        // City
        userProfileData.city?.let { city ->
            Text(
                text = "${stringResource(R.string.city_example)}$city",
                style = Theme.typography.regularAuthScreen
            )
        }

        // Birthday & Zodiac Sign
        userProfileData.birthday?.let { birthday ->
            val zodiacSign = getZodiacSign(birthday)
            Text(
                text = stringResource(R.string.birthday_example) + birthday +
                        stringResource(R.string.zodiac_example) + " " + "(" + zodiacSign + ")",
                style = Theme.typography.regularAuthScreen
            )
        }

        userProfileData.status?.let { status ->
            Text(
                text = "${stringResource(R.string.about_me_example)}$status",
                style = Theme.typography.regularAuthScreen
            )
        }

        Button(onClick = onEditClick) {
            Text(stringResource(R.string.edit_profile))
        }
    }
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