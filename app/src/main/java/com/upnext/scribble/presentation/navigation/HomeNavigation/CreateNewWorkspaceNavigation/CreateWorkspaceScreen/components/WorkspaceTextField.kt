package com.upnext.scribble.presentation.navigation.HomeNavigation.CreateNewWorkspaceNavigation.CreateWorkspaceScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            androidx.compose.material.Text(
                text = label,
                style = MaterialTheme.typography.body1,
                color = ScribbleTheme.colors.editTextSelectedTextColor
            )
        },
        modifier = modifier
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth(),
        maxLines = 1,
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
@Preview(showBackground = true)
fun WorkspaceTextFieldPreview() {
    WorkspaceTextField(
        value = "Hello Composable",
        onValueChange = {},
        label = "Name"
    )
}