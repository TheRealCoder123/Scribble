package com.upnext.scribble.presentation.navigation.HomeNavigation.Routes

sealed class HomeNavRoutes(val route: String) {

    object HomeScreen : HomeNavRoutes("home_screen")
    object CreateWorkspaceRoute: HomeNavRoutes("create_workspace_route")
    object CreateBoardRoute: HomeNavRoutes("create_board_route")
    object CreateCardRoute: HomeNavRoutes("create_card_route")

}