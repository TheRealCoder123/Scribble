package com.upnext.scribble.presentation.navigation.MainNavigation.Routes

sealed class MainGraphRoutes(val route: String) {
    object AuthRoute : MainGraphRoutes("auth_route")
    object HomeRoute : MainGraphRoutes("home_route")
}