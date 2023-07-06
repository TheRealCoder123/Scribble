package com.upnext.scribble.presentation.navigation.HomeNavigation.Drawer

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem (
    val icon: ImageVector,
    val title: String,
    val endIcon: ImageVector? = null
)