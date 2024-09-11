package com.test.mangofzcotest.presentation.navigation.home.profile.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.test.mangofzcotest.R
import com.test.mangofzcotest.domain.entities.UserProfileData
import com.test.mangofzcotest.presentation.base.ui.BlueTextButton
import com.test.mangofzcotest.presentation.base.ui.LoadingDialog
import com.test.mangofzcotest.presentation.base.ui.StateHandler
import com.test.mangofzcotest.presentation.base.ui.ToastMessage
import com.test.mangofzcotest.presentation.navigation.home.profile.ProfileViewModel
import com.test.mangofzcotest.presentation.theme.Theme
import com.test.mangofzcotest.presentation.theme.dep
import com.text.mangofzcotest.core.utils.ifNullOrBlank

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onEditClick: () -> Unit
) {
    val state by viewModel.screenState.collectAsState()

    StateHandler(
        state = state,
        loadingContent = { LoadingDialog() },
        errorContent = { error ->
            ToastMessage(error)
        },
        content = { data ->
            ProfileContent(data, onEditClick)
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
            .padding(horizontal = 24.dep, vertical = 16.dep),
        verticalArrangement = Arrangement.spacedBy(24.dep),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(140.dep)
                .clip(CircleShape)
                .border(2.dep, Color.Gray, CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = userProfileData.bigAvatar,
                contentDescription = "User Avatar",
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.image_placeholder),
                error = painterResource(id = R.drawable.image_placeholder)
            )
        }

        Text(
            text = userProfileData.username,
            style = Theme.typography.titleLarge
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Phone Icon",
                modifier = Modifier.size(20.dep),
                tint = Theme.colors.primary
            )
            Spacer(modifier = Modifier.width(8.dep))
            Text(
                text = "${stringResource(R.string.phone_example)}${userProfileData.phone}",
                style = Theme.typography.bodyRegular
            )
        }

        userProfileData.city?.let { city ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "City Icon",
                    modifier = Modifier.size(20.dep),
                    tint = Theme.colors.primary
                )
                Spacer(modifier = Modifier.width(8.dep))
                Text(
                    text = buildString {
                        append(stringResource(R.string.city_example))
                        append(city.ifNullOrBlank { stringResource(R.string.information_not_provided) })
                    },
                    style = Theme.typography.bodyRegular
                )
            }
        }

        userProfileData.birthday?.let { birthday ->
            val zodiacSign = getZodiacSign(birthday)
            val formattedBirthday = formatBirthday(birthday)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Birthday Icon",
                    modifier = Modifier.size(20.dep),
                    tint = Theme.colors.primary
                )
                Spacer(modifier = Modifier.width(8.dep))
                Text(
                    text = buildString {
                        append(stringResource(R.string.birthday_example))
                        append(formattedBirthday)
                        append("\n")
                        append(stringResource(R.string.zodiac_example))
                        append(" ($zodiacSign)")
                    },
                    style = Theme.typography.bodyRegular,
                    textAlign = TextAlign.Center
                )
            }
        }

        Text(
            text = buildString {
                append(stringResource(R.string.about_me_example))
                append(userProfileData.status.ifNullOrBlank { stringResource(R.string.information_not_provided) })
            },
            style = Theme.typography.bodyRegular,
            modifier = Modifier.padding(vertical = 16.dep)
        )

        BlueTextButton(
            onClick = onEditClick,
            text = stringResource(R.string.edit_profile),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dep),
        )
    }
}