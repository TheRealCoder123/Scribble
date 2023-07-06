package com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.upnext.scribble.data.remote.Workspace
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme

@Composable
fun SectionView(
    title: String,
    workspace: Workspace? = null,
    onClick: (Workspace?) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(ScribbleTheme.colors.editTextBackground),
    ){
        Text(
            text = title,
            color = ScribbleTheme.colors.text,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp)
                .clickable {
                    onClick(workspace)
                }
        )
    }
}