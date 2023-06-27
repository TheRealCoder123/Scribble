package com.upnext.scribble.presentation.navigation.AuthNavigation.States

data class LoginState (
    val success: String = "",
    val error: String = "",
    val loading: Boolean = false
        )