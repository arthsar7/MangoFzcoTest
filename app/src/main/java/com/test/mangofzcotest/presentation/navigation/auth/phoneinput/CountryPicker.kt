package com.test.mangofzcotest.presentation.navigation.auth.phoneinput

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.test.mangofzcotest.presentation.base.ui.BlueDropdownMenu
import com.test.mangofzcotest.presentation.theme.Theme
import com.test.mangofzcotest.presentation.theme.dep

@Composable
fun CountryPicker(
    selectedCountry: Country,
    countryList: List<Country>,
    onCountryChange: (Country) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(start = 8.dep)
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = "${selectedCountry.flag} ${selectedCountry.dialCode}",
            style = Theme.typography.textInput,
        )
        BlueDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            countryList.forEach { country ->
                DropdownMenuItem(
                    onClick = {
                        onCountryChange(country)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = "${country.flag} ${country.dialCode}",
                            style = Theme.typography.textInput
                        )
                    }
                )
            }
        }
    }
}