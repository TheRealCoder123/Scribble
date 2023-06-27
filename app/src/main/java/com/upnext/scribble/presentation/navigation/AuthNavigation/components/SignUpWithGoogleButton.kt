package com.upnext.scribble.presentation.navigation.AuthNavigation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upnext.scribble.R
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme

@Composable
fun SignUpWithGoogleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Button(
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(100.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = ScribbleTheme.colors.background,
            contentColor = ScribbleTheme.colors.text
        ),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Color.Gray),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.google_icon),
            contentDescription = stringResource(id = R.string.continue_with_google)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = stringResource(R.string.continue_with_google))
    }



}

@Composable
@Preview(showBackground = true)
fun SignUpWithGoogleButtonPreview() {
    SignUpWithGoogleButton {

    }
}