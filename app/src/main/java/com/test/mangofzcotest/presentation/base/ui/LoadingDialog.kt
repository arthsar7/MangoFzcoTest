package com.test.mangofzcotest.presentation.base.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.DialogProperties
import com.test.mangofzcotest.R
import com.test.mangofzcotest.presentation.theme.Theme
import com.test.mangofzcotest.presentation.theme.dep

@Composable
fun LoadingDialog(
    title: String = stringResource(id = R.string.loading)
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = title,
                style = Theme.typography.titleLarge,
                modifier = Modifier
                    .padding(bottom = 8.dep)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dep, horizontal = 24.dep)
                    .background(
                        color = Theme.colors.background,
                        shape = Theme.shapes.smallRoundedShapes
                    )
            ) {
                PrimaryCircularProgressIndicator()

                Spacer(modifier = Modifier.height(16.dep))

                Text(
                    text = stringResource(id = R.string.please_wait),
                    style = Theme.typography.bodyRegular,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = { /* No buttons needed because we have a loading indicator */ },
        shape = Theme.shapes.smallRoundedShapes,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        containerColor = Theme.colors.background
    )
}
