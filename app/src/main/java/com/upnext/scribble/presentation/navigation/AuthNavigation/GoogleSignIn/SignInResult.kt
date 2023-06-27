package com.upnext.scribble.presentation.navigation.AuthNavigation.GoogleSignIn

import com.upnext.scribble.data.remote.User


data class SignInResult(
    val data: User?,
    val errorMessage: String?
)
