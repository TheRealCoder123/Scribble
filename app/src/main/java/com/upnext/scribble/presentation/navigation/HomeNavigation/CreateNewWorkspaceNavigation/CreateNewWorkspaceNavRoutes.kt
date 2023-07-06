package com.upnext.scribble.presentation.navigation.HomeNavigation.CreateNewWorkspaceNavigation

import com.upnext.scribble.presentation.navigation.HomeNavigation.Routes.HomeNavRoutes

sealed class CreateNewWorkspaceNavRoutes(val route: String) {
    object CreateWorkspaceScreen : CreateNewWorkspaceNavRoutes("create_workspace_screen")
    object SuccessfullyCreatedWorkspaceScreen : CreateNewWorkspaceNavRoutes("successfully_created_workspace_screen")
}