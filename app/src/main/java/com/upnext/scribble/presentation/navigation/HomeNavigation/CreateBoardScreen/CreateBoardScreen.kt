package com.upnext.scribble.presentation.navigation.HomeNavigation.CreateBoardScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Workspaces
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.upnext.scribble.R
import com.upnext.scribble.domain.model.CreateBoardParams
import com.upnext.scribble.presentation.global_components.LoadingDialog
import com.upnext.scribble.presentation.navigation.HomeNavigation.ChooseBackgroundDialog.ChooseBgDialog
import com.upnext.scribble.presentation.navigation.HomeNavigation.CreateBoardScreen.components.ToolBar
import com.upnext.scribble.presentation.navigation.HomeNavigation.CreateNewWorkspaceNavigation.CreateWorkspaceScreen.components.WorkspaceTextField
import com.upnext.scribble.presentation.navigation.HomeNavigation.Drawer.DrawerMenuItem
import com.upnext.scribble.presentation.navigation.HomeNavigation.Drawer.MenuItem
import com.upnext.scribble.presentation.ui.theme.Blue
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme
import kotlin.random.Random

@Composable
fun CreateBoardScreen(
    navController: NavController,
    viewModel: CreateBoardScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    var isShowingAllWorkspaces by rememberSaveable {
        mutableStateOf(false)
    }

    var isChooseBgDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var bgColor by rememberSaveable {
        mutableStateOf(Random.nextLong())
    }

    val workspaceState = viewModel.workspaceState.value
    val createBoardState = viewModel.createBoardState.value

    ChooseBgDialog(isDialogOpen = isChooseBgDialogOpen) { img, color ->
        bgColor = color ?: Random.nextLong()
        isChooseBgDialogOpen = !isChooseBgDialogOpen
    }

    LoadingDialog(isDialogOpen = createBoardState.loading)

    LaunchedEffect(key1 = createBoardState.error){
        if (createBoardState.error.isNotBlank()){
            Toast.makeText(context, createBoardState.error, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = createBoardState.success){
        if (createBoardState.success.isNotBlank()){
            Toast.makeText(context, createBoardState.success, Toast.LENGTH_SHORT).show()
            navController.navigateUp()
        }
    }

    Scaffold(
        topBar = {
            ToolBar(
                onClose = {
                    navController.navigateUp()
                },
                onDone = {
                    if (viewModel.boardNameTextState.value.isEmpty()){
                        Toast.makeText(context, "Please add an a name to the board", Toast.LENGTH_SHORT).show()
                        return@ToolBar
                    }
                    if (viewModel.selectedWorkspace.value == null){
                        Toast.makeText(context, "Please select an a workspace", Toast.LENGTH_SHORT).show()
                        return@ToolBar
                    }
                    viewModel
                        .createBoard(
                            CreateBoardParams(
                                viewModel.boardNameTextState.value,
                                viewModel.selectedWorkspace.value?.workspaceId ?: "",
                                bgColor,
                                ""
                            )
                        )
                }
            )
        },
        backgroundColor = ScribbleTheme.colors.background,
        drawerShape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp)
    ) {
        it

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            item {

                WorkspaceTextField(
                    value = viewModel.boardNameTextState.value,
                    onValueChange = { newName -> viewModel.boardNameTextState.value = newName },
                    label = "Board Name",
                    modifier = Modifier.padding(0.dp)
                )

                Column() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(ScribbleTheme.colors.editTextBackground)
                            .padding(15.dp)
                            .clickable {
                                if (!workspaceState.loading) {
                                    isShowingAllWorkspaces = !isShowingAllWorkspaces
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (viewModel.selectedWorkspace.value != null)
                                viewModel.selectedWorkspace.value?.displayName ?: stringResource(id = R.string.pick_an_workspace)
                            else
                                stringResource(id = R.string.pick_an_workspace),
                            color = ScribbleTheme.colors.text
                        )
                        if (workspaceState.loading){
                            CircularProgressIndicator(
                                modifier = Modifier.size(30.dp)
                            )
                        }else{
                            if (isShowingAllWorkspaces){
                                Icon(
                                    imageVector = Icons.Default.ArrowDropUp,
                                    contentDescription = stringResource(R.string.pick_an_workspace),
                                    tint = ScribbleTheme.colors.text
                                )
                            }else{
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = stringResource(R.string.pick_an_workspace),
                                    tint = ScribbleTheme.colors.text
                                )
                            }
                        }
                    }
                    if (isShowingAllWorkspaces && workspaceState.data.isNotEmpty()){

                        workspaceState.data.forEach { workspace->
                            DrawerMenuItem(
                                item = MenuItem(
                                    Icons.Default.Workspaces,
                                    workspace.displayName
                                ),
                                tint = if (viewModel.selectedWorkspace.value?.workspaceId == workspace.workspaceId){
                                    Blue
                                }else{
                                    ScribbleTheme.colors.text
                                }
                            ){
                                viewModel.selectedWorkspace.value = workspace
                            }
                        }

                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                        .clickable {
                            isChooseBgDialogOpen = !isChooseBgDialogOpen
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Board Background",
                        color = ScribbleTheme.colors.text
                    )
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(bgColor))
                            .size(35.dp)
                    )
                }


            }



        }


    }




}



@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CreateBoardScreenPreview() {
    CreateBoardScreen(rememberNavController(), hiltViewModel())
}

