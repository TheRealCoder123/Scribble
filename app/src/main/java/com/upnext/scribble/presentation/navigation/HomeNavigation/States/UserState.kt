package com.upnext.scribble.presentation.navigation.HomeNavigation.States

import com.upnext.scribble.data.remote.User

data class UserState(
    val user: User? = null,
    val error: String = "",
    val loading: Boolean = false
)
