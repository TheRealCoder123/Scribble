package com.upnext.scribble.presentation.navigation.HomeNavigation.Drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Workspaces
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.upnext.scribble.data.remote.Workspace
import com.upnext.scribble.presentation.navigation.HomeNavigation.States.WorkspacesState
import com.upnext.scribble.presentation.ui.theme.Blue
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme

@Composable
fun DrawerWorkspacesBody(
    workspaceState: WorkspacesState,
    onAllBoardsViewClicked: () -> Unit,
    onWorkspaceClicked: (Workspace) -> Unit,
    onCreateNewWorkspaceClicked: () -> Unit,
    isBoardsSelected: Boolean,
    selectedWorkspaceId: String
) {
   Column(
       modifier = Modifier.fillMaxWidth()
   ) {


       DrawerMenuItem(
           item = MenuItem(
               Icons.Default.Add,
               "Create New Workspace"
           ),
           onItemClicked = {
               onCreateNewWorkspaceClicked()
           }
       )

       DrawerMenuItem(
           item = MenuItem(
               Icons.Default.Dashboard,
               "Boards"
           ),
           onItemClicked = {
               onAllBoardsViewClicked()
           },
           tint = if (isBoardsSelected) Blue else ScribbleTheme.colors.drawerBodyOnBackground
       )

       Divider(
           modifier = Modifier.fillMaxWidth(),
           thickness = 1.dp,
           Color.LightGray
       )

       Text(
           text = "Workspaces",
           color = ScribbleTheme.colors.text,
           modifier = Modifier.padding(10.dp)
       )

       if (workspaceState.data.isNotEmpty()){
           workspaceState.data.distinct().forEach {workspace->
               DrawerMenuItem(
                   item = MenuItem(
                       Icons.Default.Workspaces,
                       workspace.displayName,
                       Icons.Default.MoreHoriz
                   ),
                   tint = if (
                       selectedWorkspaceId.isNotBlank() &&
                       workspace.workspaceId == selectedWorkspaceId
                   ) Blue else ScribbleTheme.colors.drawerBodyOnBackground,
                   onItemClicked = {
                       onWorkspaceClicked(workspace)
                   }
               )
           }
       }else{
           Box(modifier = Modifier
               .fillMaxSize()
               .padding(10.dp),
               contentAlignment = Alignment.Center
           ){
               Column(
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.Center
               ) {
                   Text(
                       text = "You do not have any workspaces",
                       color = ScribbleTheme.colors.text
                   )
                   Spacer(modifier = Modifier.height(10.dp))
                   Text(
                       text = "Create New Workspace?",
                       color = Blue,
                       modifier = Modifier.clickable{
                           onCreateNewWorkspaceClicked()
                       }
                   )
               }
           }
       }

   }
}