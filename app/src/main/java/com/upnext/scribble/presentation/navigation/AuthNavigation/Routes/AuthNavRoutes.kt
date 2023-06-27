package com.upnext.scribble.presentation.navigation.AuthNavigation.Routes

sealed class AuthNavRoutes(val route: String) {
    object LoginScreen : AuthNavRoutes("login_screen")
    object SignUpRoute : AuthNavRoutes("sing_up_screen")
    object ForgotPasswordScreen : AuthNavRoutes("forgot_password_screen")
}