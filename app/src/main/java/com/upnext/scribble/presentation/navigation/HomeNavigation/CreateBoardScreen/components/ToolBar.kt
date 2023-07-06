package com.upnext.scribble.presentation.navigation.HomeNavigation.CreateBoardScreen.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.upnext.scribble.R
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme

@Composable
fun ToolBar(
    onClose: () -> Unit,
    onDone: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                onClose()
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close),
                    tint = Color.White
                )
            }
        },
        title = {
            Text(
                text = "Create Board",
                color = Color.White
            )
        },
        actions = {
            IconButton(onClick = {
                onDone()
            }) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(R.string.done),
                    tint = Color.White
                )
            }
        },
        backgroundColor = ScribbleTheme.colors.primary
    )
}