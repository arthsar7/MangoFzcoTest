package com.test.mangofzcotest.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.test.mangofzcotest.domain.entities.UserProfileData

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onEditClick: () -> Unit
) {
    val profileState by viewModel.profileState.collectAsState()

    profileState?.let { profileData ->
        ProfileContent(profileData, onEditClick)
    } ?: run {
        // Display a loading or empty state
        Text(text = "Loading profile...")
    }
}

@Composable
fun ProfileContent(
    userProfileData: UserProfileData,
    onEditClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Avatar
        userProfileData.avatar?.let { avatarUrl ->
            AsyncImage(
                model = avatarUrl,
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
        }

        // Username
        Text(text = userProfileData.username, style = MaterialTheme.typography.headlineMedium)

        // Phone
        Text(text = "Phone: ${userProfileData.phone}", style = MaterialTheme.typography.bodyMedium)

        // City
        userProfileData.city?.let { city ->
            Text(text = "City: $city", style = MaterialTheme.typography.bodyMedium)
        }

        // Birthday & Zodiac Sign
        userProfileData.birthday?.let { birthday ->
            val zodiacSign = getZodiacSign(birthday)
            Text(text = "Birthday: $birthday (Zodiac: $zodiacSign)", style = MaterialTheme.typography.bodyMedium)
        }

        // About Me / Status
        userProfileData.status?.let { status ->
            Text(text = "About me: $status", style = MaterialTheme.typography.bodyMedium)
        }

        Button(onClick = onEditClick) {
            Text("Edit Profile")
        }
    }
}

// Utility function to get Zodiac sign from birthday
fun getZodiacSign(birthday: String): String {
    // Calculate zodiac sign based on the birthday (for simplicity, assume birthday is in the format "yyyy-MM-dd")
    val month = birthday.substring(5, 7).toInt()
    val day = birthday.substring(8, 10).toInt()

    return when {
        (month == 1 && day >= 20) || (month == 2 && day <= 18) -> "Aquarius"
        month == 2 || (month == 3 && day <= 20) -> "Pisces"
        month == 3 || (month == 4 && day <= 19) -> "Aries"
        month == 4 || (month == 5 && day <= 20) -> "Taurus"
        month == 5 || (month == 6 && day <= 20) -> "Gemini"
        month == 6 || (month == 7 && day <= 22) -> "Cancer"
        month == 7 || (month == 8 && day <= 22) -> "Leo"
        month == 8 || (month == 9 && day <= 22) -> "Virgo"
        month == 9 || (month == 10 && day <= 22) -> "Libra"
        month == 10 || (month == 11 && day <= 21) -> "Scorpio"
        month == 11 || (month == 12 && day <= 21) -> "Sagittarius"
        else -> "Capricorn"
    }
}