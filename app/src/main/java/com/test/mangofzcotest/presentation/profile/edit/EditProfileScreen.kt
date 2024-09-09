package com.test.mangofzcotest.presentation.profile.edit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.test.mangofzcotest.presentation.profile.ProfileViewModel
import java.io.ByteArrayOutputStream

@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onProfileUpdated: () -> Unit // A callback for when profile is successfully updated
) {
    val profileState by viewModel.profileState.collectAsState()
    var name by remember { mutableStateOf(profileState?.name ?: "") }
    var birthday by remember { mutableStateOf(profileState?.birthday ?: "") }
    var city by remember { mutableStateOf(profileState?.city ?: "") }
    var vk by remember { mutableStateOf(profileState?.vk ?: "") }
    var instagram by remember { mutableStateOf(profileState?.instagram ?: "") }
    var status by remember { mutableStateOf(profileState?.status ?: "") }
    var avatarBase64 by remember { mutableStateOf("") }
    var avatarFileName by remember { mutableStateOf("avatar.png") } // default file name

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Avatar upload section
        AvatarUpload(avatarBase64, onAvatarSelected = { base64, fileName ->
            avatarBase64 = base64
            avatarFileName = fileName
        })

        // Editable fields
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        OutlinedTextField(
            value = birthday,
            onValueChange = { birthday = it },
            label = { Text("Birthday (yyyy-mm-dd)") }
        )
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") }
        )
        OutlinedTextField(
            value = vk,
            onValueChange = { vk = it },
            label = { Text("VK") }
        )
        OutlinedTextField(
            value = instagram,
            onValueChange = { instagram = it },
            label = { Text("Instagram") }
        )
        OutlinedTextField(
            value = status,
            onValueChange = { status = it },
            label = { Text("About Me") }
        )

        // Save button
        Button(onClick = {
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
                username = profileState?.username ?: ""
            )
        }) {
            Text("Save Changes")
        }
    }
}

@Composable
fun AvatarUpload(
    avatarBase64: String,
    onAvatarSelected: (String, String) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val base64 = context.encodeImageToBase64(it)
                val fileName = "avatar.png" // This could be extracted from the uri if necessary
                onAvatarSelected(base64, fileName)
            }
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (avatarBase64.isNotEmpty()) {
            AsyncImage(
                model = decodeBase64ToBitmap(avatarBase64),
                contentDescription = "Uploaded Avatar",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
        } else {
            Text("No Avatar Selected")
        }
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Upload Avatar")
        }
    }
}

// Functions to encode image to base64
fun Context.encodeImageToBase64(uri: Uri): String {
    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

// Decode base64 string to bitmap for preview
fun decodeBase64ToBitmap(base64Str: String): Bitmap {
    val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}
