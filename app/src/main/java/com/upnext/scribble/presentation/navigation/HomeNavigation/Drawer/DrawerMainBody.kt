package com.upnext.scribble.presentation.navigation.HomeNavigation.Drawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DrawerMainBody(
    drawerItems: List<MenuItem>,
    onDrawerItemClick: (MenuItem) -> (Unit)
) {
    Spacer(modifier = Modifier.height(10.dp))
    drawerItems.forEach {item ->
        DrawerMenuItem(item = item){
            onDrawerItemClick(it)
        }
    }
}