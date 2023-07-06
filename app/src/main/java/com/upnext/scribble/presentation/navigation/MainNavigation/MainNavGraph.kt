package com.upnext.scribble.presentation.navigation.MainNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.upnext.scribble.common.Firebase
import com.upnext.scribble.common.Helpers.sharedViewModel
import com.upnext.scribble.presentation.navigation.AuthNavigation.AuthViewModel
import com.upnext.scribble.presentation.navigation.AuthNavigation.ForgotPassword.ForgotPasswordScreen
import com.upnext.scribble.presentation.navigation.AuthNavigation.LoginScreen.LoginScreen
import com.upnext.scribble.presentation.navigation.AuthNavigation.Routes.AuthNavRoutes
import com.upnext.scribble.presentation.navigation.AuthNavigation.SignUpScreen.SignUpScreen
import com.upnext.scribble.presentation.navigation.HomeNavigation.CreateBoardScreen.CreateBoardScreen
import com.upnext.scribble.presentation.navigation.HomeNavigation.CreateCardScreen.CreateCardScreen
import com.upnext.scribble.presentation.navigation.HomeNavigation.CreateNewWorkspaceNavigation.CreateNewWorkspaceNavRoutes
import com.upnext.scribble.presentation.navigation.HomeNavigation.CreateNewWorkspaceNavigation.CreateWorkspaceScreen.CreateWorkspaceScreen
import com.upnext.scribble.presentation.navigation.HomeNavigation.CreateNewWorkspaceNavigation.CreateWorkspaceViewModel
import com.upnext.scribble.presentation.navigation.HomeNavigation.CreateNewWorkspaceNavigation.SuccessfullyCreatedWorkspaceScreen.SuccessfullyCreatedWorkspaceScreen
import com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen.HomeScreen
import com.upnext.scribble.presentation.navigation.HomeNavigation.HomeViewModel
import com.upnext.scribble.presentation.navigation.HomeNavigation.Routes.HomeNavRoutes
import com.upnext.scribble.presentation.navigation.MainNavigation.Routes.MainGraphRoutes

@Composable
fun MainNavigationGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = if(Firebase.isUserLoggedIn()) MainGraphRoutes.HomeRoute.route else MainGraphRoutes.AuthRoute.route
    ){
        navigation(
            startDestination = AuthNavRoutes.LoginScreen.route,
            route = MainGraphRoutes.AuthRoute.route
        ){
            composable(AuthNavRoutes.LoginScreen.route){
                val authVm = it.sharedViewModel<AuthViewModel>(navController = navHostController)
                LoginScreen(authVm, navHostController)
            }
            composable(AuthNavRoutes.SignUpRoute.route){
                val authVm = it.sharedViewModel<AuthViewModel>(navController = navHostController)
                SignUpScreen(authVm, navHostController)
            }
            composable(AuthNavRoutes.ForgotPasswordScreen.route){
                val authVm = it.sharedViewModel<AuthViewModel>(navController = navHostController)
                ForgotPasswordScreen(authVm, navHostController)
            }
        }
        navigation(
            startDestination = HomeNavRoutes.HomeScreen.route,
            route = MainGraphRoutes.HomeRoute.route
        ){
            composable(HomeNavRoutes.HomeScreen.route){
                val homeViewModel = it.sharedViewModel<HomeViewModel>(navController = navHostController)
                HomeScreen(navController = navHostController, homeViewModel = homeViewModel)
            }

            composable(HomeNavRoutes.CreateBoardRoute.route){
                CreateBoardScreen(navController = navHostController)
            }

            composable(HomeNavRoutes.CreateCardRoute.route){
                CreateCardScreen()
            }

            navigation(
                CreateNewWorkspaceNavRoutes.CreateWorkspaceScreen.route,
                HomeNavRoutes.CreateWorkspaceRoute.route
            ){
                composable(CreateNewWorkspaceNavRoutes.CreateWorkspaceScreen.route){
                    val createWorkspaceVm = it.sharedViewModel<CreateWorkspaceViewModel>(navController = navHostController)
                    CreateWorkspaceScreen(navHostController, viewModel = createWorkspaceVm)
                }
                composable(CreateNewWorkspaceNavRoutes.SuccessfullyCreatedWorkspaceScreen.route){
                    val createWorkspaceVm = it.sharedViewModel<CreateWorkspaceViewModel>(navController = navHostController)
                    SuccessfullyCreatedWorkspaceScreen(createWorkspaceVm, navController = navHostController)
                }
            }



        }
    }

}