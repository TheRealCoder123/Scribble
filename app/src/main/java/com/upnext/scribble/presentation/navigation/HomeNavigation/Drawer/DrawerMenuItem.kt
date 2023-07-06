package com.upnext.scribble.presentation.navigation.HomeNavigation.Drawer

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upnext.scribble.R
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme


@Composable
fun DrawerMenuItem(
    item: MenuItem,
    modifier : Modifier = Modifier,
    tint: Color = ScribbleTheme.colors.drawerBodyOnBackground,
    onItemClicked: (MenuItem) -> (Unit) = {},
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(10.dp)
            .clickable {
                onItemClicked(item)
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = tint
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = item.title,
                color = tint
            )
        }

        item.endIcon?.let {
            Icon(
                imageVector = item.endIcon,
                contentDescription = item.title,
                tint = tint
            )
        }

    }
}

@Composable
@Preview
fun DrawerMenuItemPreview() {
    DrawerMenuItem(
        MenuItem(
            Icons.Default.CardMembership,
            stringResource(R.string.my_cards)
        )
    )
}