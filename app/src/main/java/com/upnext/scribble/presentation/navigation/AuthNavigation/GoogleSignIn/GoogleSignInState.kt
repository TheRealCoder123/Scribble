package com.upnext.scribble.presentation.navigation.AuthNavigation.GoogleSignIn

import com.upnext.scribble.data.remote.User
import kotlinx.coroutines.flow.MutableStateFlow

data class GoogleSignInState(
    val data: User? = null,
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
