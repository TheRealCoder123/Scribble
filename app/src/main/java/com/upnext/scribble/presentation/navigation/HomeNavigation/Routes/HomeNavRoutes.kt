package com.upnext.scribble.presentation.navigation.HomeNavigation.Routes

sealed class HomeNavRoutes(val route: String) {

    object HomeScreen : HomeNavRoutes("home_screen")

}