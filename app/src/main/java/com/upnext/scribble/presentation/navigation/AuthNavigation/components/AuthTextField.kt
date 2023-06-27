package com.upnext.scribble.presentation.navigation.AuthNavigation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    inputType: KeyboardType
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.body1,
                color = ScribbleTheme.colors.editTextSelectedTextColor
            )
        },
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = inputType),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = ScribbleTheme.colors.editTextBackground,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            textColor = ScribbleTheme.colors.editTextColor,
            cursorColor = ScribbleTheme.colors.selectedColor
        )
    )

}

@Composable
@Preview
fun AuthTextFieldPreview() {
    AuthTextField(
        "Hello World",
        {

        },
        "Email",
        inputType = KeyboardType.Email
    )
}