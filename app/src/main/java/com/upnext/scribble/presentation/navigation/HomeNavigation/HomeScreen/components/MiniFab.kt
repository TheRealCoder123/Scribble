package com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upnext.scribble.presentation.navigation.HomeNavigation.States.MultiFabState
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme


data class MiniFabItem(
    val icon: ImageVector,
    val label: String,
    val id: Int
)

@Composable
fun MiniFab(
    item: MiniFabItem,
    textShadow: Dp,
    showLabel: Boolean = true,
    rotate: Float,
    onClick: (MiniFabItem) -> Unit
) {

    val buttonColor = ScribbleTheme.colors.secondaryColor

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(end = 10.dp)
    ) {

        if (showLabel) {
            Text(
                text = item.label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .shadow(textShadow)
                    .background(Color.White)
                    .padding(start = 6.dp, end = 6.dp, top = 4.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }

        FloatingActionButton(
            onClick = {
                onClick(item)
            },
            backgroundColor = ScribbleTheme.colors.secondaryColor,
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.rotate(rotate).size(40.dp),
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = Color.White,
            )
        }
    }

}