package com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen

import android.widget.Toast
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material.IconButton
import  androidx.compose.material.Scaffold
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CardMembership
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.auth.FirebaseAuth
import com.upnext.scribble.R
import com.upnext.scribble.common.WindowInfo
import com.upnext.scribble.common.rememberWindowInfo
import com.upnext.scribble.presentation.navigation.HomeNavigation.Drawer.DrawerHeader
import com.upnext.scribble.presentation.navigation.HomeNavigation.Drawer.DrawerMainBody
import com.upnext.scribble.presentation.navigation.HomeNavigation.Drawer.DrawerWorkspacesBody
import com.upnext.scribble.presentation.navigation.HomeNavigation.Drawer.MenuItem
import com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen.components.AllBoardsView
import com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen.components.HomeBoardView
import com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen.components.HomeBoxBoardView
import com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen.components.MiniFabItem
import com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen.components.MultiFabButton
import com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen.components.SectionView
import com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen.components.WorkspaceBoardsView
import com.upnext.scribble.presentation.navigation.HomeNavigation.HomeViewModel
import com.upnext.scribble.presentation.navigation.HomeNavigation.Routes.HomeNavRoutes
import com.upnext.scribble.presentation.navigation.HomeNavigation.States.HomeScreenDisplayState
import com.upnext.scribble.presentation.navigation.HomeNavigation.States.MultiFabState
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController
) {

    val userDataState = homeViewModel.userState.value
    val workspaceState = homeViewModel.workspaceState.value

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val windowInfo = rememberWindowInfo()

    var fabState by rememberSaveable {
        mutableStateOf(MultiFabState.Collapsed)
    }

    val context = LocalContext.current

    var shouldShowWorkSpaces by rememberSaveable {
        mutableStateOf(false)
    }

    var isRefreshing by rememberSaveable {
        mutableStateOf(false)
    }

    val boardsState = homeViewModel.boardsState.value
    val allBoardsState = homeViewModel.homeWorkspaceSections.value

    LaunchedEffect(key1 = isRefreshing){
        if(isRefreshing){
            homeViewModel.getBoardsByAllWorkspaces()
            isRefreshing = false
        }
    }


   Scaffold(
       scaffoldState = scaffoldState,
       backgroundColor = ScribbleTheme.colors.background,
       drawerBackgroundColor = ScribbleTheme.colors.drawerBodyBackground,
       drawerContent = {
           LazyColumn(modifier = Modifier
               .fillMaxWidth()
           ){
               item {
                   DrawerHeader(
                       userDataState,
                       onShowWorkSpacesClicked = {
                           shouldShowWorkSpaces = !shouldShowWorkSpaces
                       },
                       onShowWorkSpacesIconChange = {
                           if (shouldShowWorkSpaces){
                               Icons.Default.KeyboardArrowUp
                           }else{
                               Icons.Default.KeyboardArrowDown
                           }
                       }
                   )
                   if (shouldShowWorkSpaces){
                       DrawerWorkspacesBody(
                           workspaceState,
                           onCreateNewWorkspaceClicked = {
                               if (userDataState.user != null){
                                   navController.navigate(HomeNavRoutes.CreateWorkspaceRoute.route)
                               }else{
                                   Toast.makeText(
                                       context,
                                       "We dont have your data yet, try again!",
                                       Toast.LENGTH_SHORT
                                   ).show()
                               }
                           },
                           onAllBoardsViewClicked = {
                               homeViewModel.dataDisplayState.value = HomeScreenDisplayState.ALL_BOARDS
                               homeViewModel.selectedWorkspace.value = null
                               homeViewModel.getBoardsByAllWorkspaces()
                           },
                           onWorkspaceClicked = {
                               homeViewModel.dataDisplayState.value = HomeScreenDisplayState.WORKSPACE
                               homeViewModel.selectedWorkspace.value = it
                               homeViewModel.getBoards()
                           },
                           isBoardsSelected = homeViewModel.dataDisplayState.value == HomeScreenDisplayState.ALL_BOARDS,
                           selectedWorkspaceId = homeViewModel.selectedWorkspace.value?.workspaceId ?: ""
                       )
                   }else{
                       DrawerMainBody(
                           listOf(
                               MenuItem(
                                   Icons.Default.CardMembership,
                                   stringResource(R.string.my_cards)
                               ),
                               MenuItem(
                                   Icons.Default.Dashboard,
                                   stringResource(R.string.offline_boards)
                               ),
                               MenuItem(
                                   Icons.Default.Settings,
                                   stringResource(R.string.settings),
                               ),
                               MenuItem(
                                   Icons.Default.Help,
                                   stringResource(R.string.help)
                               ),
                           )
                       ){ item->

                       }
                   }
               }
           }
       },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if(
                            homeViewModel.selectedWorkspace.value == null &&
                            homeViewModel.dataDisplayState.value == HomeScreenDisplayState.ALL_BOARDS
                        )
                            "Boards"
                        else
                            homeViewModel.selectedWorkspace.value!!.displayName,
                        color = Color.White
                    )
                },
                backgroundColor = ScribbleTheme.colors.primary,
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Toggle drawer",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    TopAppBarActions()
                }
            )
        },
       floatingActionButton = {
           MultiFabButton(
               multiFabState = fabState,
               onStateChanged = {
                   fabState = it
               },
               items = listOf(
                   MiniFabItem(
                       icon = Icons.Default.Dashboard,
                       label = "Board",
                       id = 1
                   ),
                   MiniFabItem(
                       icon = Icons.Default.CardMembership,
                       label = "Card",
                       id = 2
                   )
               ),
               navController
           )
       }
    ) {
        it

       if (fabState == MultiFabState.Expanded){
           Box(
               modifier = Modifier
                   .fillMaxSize()
                   .background(Color.Black.copy(0.03f))
           )
       }


       when(homeViewModel.dataDisplayState.value){
           HomeScreenDisplayState.ALL_BOARDS -> {
               if (allBoardsState.sections.isNotEmpty()){
                   if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact){
                       SwipeRefresh(
                           state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                           onRefresh = {
                               isRefreshing = true
                           }
                       ) {
                           AllBoardsView(sections = allBoardsState.sections)
                       }
                   }else{
                       SwipeRefresh(
                           state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                           onRefresh = {
                               isRefreshing = true
                           }
                       ) {
                           LazyColumn(
                               modifier = Modifier.fillMaxSize()
                           ) {
                               item {
                                   allBoardsState.sections.forEach { section ->
                                       SectionView(
                                           title = section.section.displayName,
                                           workspace = section.section
                                       ){

                                       }
                                       com.google.accompanist.flowlayout.FlowRow(
                                           mainAxisSize = SizeMode.Expand,
                                           mainAxisAlignment = FlowMainAxisAlignment.Start
                                       ) {
                                           section.boards.forEach { board ->
                                               HomeBoxBoardView(
                                                   board = board,
                                                   width = windowInfo.screenWidth / 4,
                                                   height = windowInfo.screenHeight / 3
                                               )
                                           }
                                       }
                                   }
                               }
                           }
                       }
                   }
               }else{
                   Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                       Text(
                           text = "Please create an workspace to see the boards",
                           color = Color.Gray
                       )
                   }
               }
           }
           HomeScreenDisplayState.WORKSPACE -> {
               if(boardsState.boards.isNotEmpty()){
                   WorkspaceBoardsView(boardsState.boards)
               }else{
                   Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                       Text(
                           text = "This workspace does not have any boards",
                           color = Color.Gray
                       )
                   }
               }
           }
       }







    }



}

@Composable
fun TopAppBarActions() {
    IconButton(onClick = {

    }) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = Color.White
        )
    }
    IconButton(onClick = {

    }) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications",
            tint = Color.White
        )
    }
    IconButton(onClick = {

    }) {
        Icon(
            imageVector = Icons.Default.MoreHoriz,
            contentDescription = "More",
            tint = Color.White
        )
    }
}



@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController(), homeViewModel = hiltViewModel())
}