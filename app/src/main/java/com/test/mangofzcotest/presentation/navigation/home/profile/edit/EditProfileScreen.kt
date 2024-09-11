package com.test.mangofzcotest.presentation.navigation.home.profile.edit

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.test.mangofzcotest.R
import com.test.mangofzcotest.domain.entities.UserProfileData
import com.test.mangofzcotest.presentation.base.ui.BlueTextButton
import com.test.mangofzcotest.presentation.base.ui.BlueTextField
import com.test.mangofzcotest.presentation.base.ui.LoadingDialog
import com.test.mangofzcotest.presentation.base.ui.StateHandler
import com.test.mangofzcotest.presentation.base.ui.ToastMessage
import com.test.mangofzcotest.presentation.navigation.home.profile.ProfileViewModel
import com.test.mangofzcotest.presentation.theme.dep

@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel,
    onProfileUpdated: () -> Unit
) {
    val state by viewModel.screenState.collectAsState()
    StateHandler(
        state = state,
        loadingContent = {
            LoadingDialog()
        },
        errorContent = { error ->
            ToastMessage(error)
        },
        content = { userProfileData ->
            ProfileContent(userProfileData, viewModel, onProfileUpdated)
        }
    )
}

@Composable
private fun ProfileContent(
    profileState: UserProfileData,
    viewModel: ProfileViewModel,
    onProfileUpdated: () -> Unit
) {
    var name by remember { mutableStateOf(profileState.name) }
    var birthday by remember { mutableStateOf(profileState.birthday.orEmpty()) }
    var city by remember { mutableStateOf(profileState.city.orEmpty()) }
    var vk by remember { mutableStateOf(profileState.vk.orEmpty()) }
    var instagram by remember { mutableStateOf(profileState.instagram.orEmpty()) }
    var status by remember { mutableStateOf(profileState.status.orEmpty()) }
    var avatarBase64 by remember { mutableStateOf(profileState.avatarBase64.orEmpty()) }
    var avatarFileName by remember { mutableStateOf("avatar.png") }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dep)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dep),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AvatarUpload(
            avatarUrl = profileState.bigAvatar,
            avatarBase64 = avatarBase64,
            onAvatarSelected = { base64, fileName ->
                avatarBase64 = base64
                avatarFileName = fileName
            }
        )

        BlueTextField(
            value = name,
            onValueChange = { name = it },
            placeholderText = stringResource(R.string.name),
        )

        BlueTextField(
            value = birthday,
            onValueChange = {},
            placeholderText = birthday.ifBlank { stringResource(R.string.birthday_yyyy_mm_dd) },
            readOnly = true,
            modifier = Modifier.clickable {
                showDatePickerDialog(context) { selectedDate ->
                    birthday = selectedDate
                }
            },
            enabled = false
        )

        BlueTextField(
            value = city,
            onValueChange = { city = it },
            placeholderText = stringResource(R.string.city),
        )
        BlueTextField(
            value = vk,
            onValueChange = { vk = it },
            placeholderText = stringResource(R.string.vk),
        )
        BlueTextField(
            value = instagram,
            onValueChange = { instagram = it },
            placeholderText = stringResource(R.string.instagram),
        )
        BlueTextField(
            value = status,
            onValueChange = { status = it },
            placeholderText = stringResource(R.string.about_me),
        )

        // Save button
        BlueTextButton(
            onClick = {
                viewModel.updateProfile(
                    name = name,
                    birthday = birthday,
                    city = city,
                    vk = vk,
                    instagram = instagram,
                    status = status,
                    avatarFileName = avatarFileName,
                    avatarBase64 = avatarBase64,
                    onProfileUpdated = onProfileUpdated,
                    username = profileState.username
                )
            },
            text = stringResource(R.string.save_changes)
        )
    }
}

@Composable
fun AvatarUpload(
    avatarUrl: String?,
    avatarBase64: String,
    onAvatarSelected: (String, String) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val base64 = context.encodeImageToBase64(it)
                val fileName = it.pathSegments.lastOrNull() ?: "avatar.jpg"
                onAvatarSelected(base64, fileName)
            }
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (avatarBase64.isNotBlank()) {
            AsyncImage(
                model = decodeBase64ToBitmap(avatarBase64),
                contentDescription = "Uploaded Avatar",
                modifier = Modifier
                    .size(140.dep)
                    .clip(CircleShape)
                    .border(2.dep, Color.Gray, CircleShape)
                    .clickable {
                        launcher.launch("image/*")
                    },
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.image_placeholder),
                error = painterResource(id = R.drawable.image_placeholder)
            )
        } else {
            AsyncImage(
                model = avatarUrl,
                contentDescription = "Uploaded Avatar",
                modifier = Modifier
                    .size(140.dep)
                    .clip(CircleShape)
                    .border(2.dep, Color.Gray, CircleShape)
                    .clickable {
                        launcher.launch("image/*")
                    },
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.image_placeholder),
                error = painterResource(id = R.drawable.image_placeholder)
            )
        }
    }
}