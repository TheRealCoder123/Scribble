package com.upnext.scribble.presentation.navigation.HomeNavigation.CreateNewWorkspaceNavigation.CreateWorkspaceScreen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.upnext.scribble.R
import com.upnext.scribble.domain.model.CreateWorkspaceParams
import com.upnext.scribble.presentation.global_components.CircleImageView
import com.upnext.scribble.presentation.global_components.LoadingDialog
import com.upnext.scribble.presentation.navigation.HomeNavigation.CreateNewWorkspaceNavigation.CreateNewWorkspaceNavRoutes
import com.upnext.scribble.presentation.navigation.HomeNavigation.CreateNewWorkspaceNavigation.CreateWorkspaceScreen.components.WorkspaceTextField
import com.upnext.scribble.presentation.navigation.HomeNavigation.CreateNewWorkspaceNavigation.CreateWorkspaceViewModel
import com.upnext.scribble.presentation.navigation.HomeNavigation.HomeViewModel
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme

@Composable
fun CreateWorkspaceScreen(
    navController: NavController,
    viewModel: CreateWorkspaceViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {

    val userState = homeViewModel.userState.value
    val createWorkspaceState = viewModel.createWorkspaceState.value

    var loadingDialogState by remember {
        mutableStateOf(false)
    }

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            viewModel.workspaceImageUri.value = uri
        })

    LaunchedEffect(key1 = createWorkspaceState.loading){
        loadingDialogState = createWorkspaceState.loading
    }

    LaunchedEffect(key1 = createWorkspaceState.error){
        if (createWorkspaceState.error.isNotBlank()){
            Toast.makeText(context, createWorkspaceState.error, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = createWorkspaceState.success){
        if (createWorkspaceState.success.isNotBlank()){
            Toast.makeText(context, createWorkspaceState.success, Toast.LENGTH_SHORT).show()
            navController.navigate(
                CreateNewWorkspaceNavRoutes.SuccessfullyCreatedWorkspaceScreen.route
            ){
                popUpTo(CreateNewWorkspaceNavRoutes.CreateWorkspaceScreen.route){
                    inclusive = true
                }
            }
        }
    }

    LoadingDialog(isDialogOpen = loadingDialogState)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.create_new_workspace),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.close),
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = ScribbleTheme.colors.primary,
                actions = {
                    IconButton(onClick = {

                        if (viewModel.nameTextFieldState.value.isEmpty()){
                            Toast.makeText(context, "Please enter the name of this work space", Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        if (viewModel.workspaceImageUri.value == null){
                            Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        viewModel.createWorkspaceParamsState.value = CreateWorkspaceParams(
                            viewModel.nameTextFieldState.value,
                            viewModel.workspaceImageUri.value.toString()
                        )
                        viewModel.createWorkspace(
                            CreateWorkspaceParams(
                                viewModel.nameTextFieldState.value,
                                viewModel.workspaceImageUri.value.toString()
                            )
                        )

                    }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Create Workspace",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        backgroundColor = ScribbleTheme.colors.background
    ) {
        it

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .scrollable(scrollState, Orientation.Vertical)
        ) {
            Box(modifier = Modifier
                .size(70.dp)
                .border(1.dp, Color.LightGray, CircleShape),
                contentAlignment = Alignment.Center
            ){
                ImageSelector(
                    uri = viewModel.workspaceImageUri.value,
                    launcher = launcher
                )
            }
            WorkspaceTextField(
                value = viewModel.nameTextFieldState.value,
                label = "Workspace Name",
                onValueChange = { textResult ->
                    viewModel.nameTextFieldState.value = textResult
                }
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = stringResource(R.string.creator),
                color = ScribbleTheme.colors.text
            )

            Spacer(modifier = Modifier.width(10.dp))


            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(ScribbleTheme.colors.editTextBackground)
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {


                CircleImageView(
                    image = userState.user?.image,
                    size = 50.dp,
                    modifier = Modifier
                        .border(
                            1.dp,
                            Color.LightGray,
                            RoundedCornerShape(70.dp)
                        )
                )

                Spacer(modifier = Modifier.width(20.dp))

                Column {
                    Text(
                        text = userState.user?.displayName ?: "",
                        color = ScribbleTheme.colors.text,
                        fontSize = 20.sp
                    )
                    Text(
                        text = userState.user?.username ?: "",
                        color = ScribbleTheme.colors.text,
                        fontSize = 15.sp
                    )
                }

            }

        }


    }

}

@Composable
fun ImageSelector(
    uri: Uri?,
    launcher: ManagedActivityResultLauncher<Array<String>, Uri?>
) {
   if (uri != null){
       Row(
           horizontalArrangement = Arrangement.Center,
       ) {
           CircleImageView(
               image = uri,
               size = 70.dp,
               modifier = Modifier
                   .border(
                       1.dp,
                       Color.LightGray,
                       RoundedCornerShape(70.dp)
                   )
                   .clickable {
                       launcher.launch(arrayOf("image/*"))
                   }
           )
       }
   }else{
       IconButton(onClick = {
           launcher.launch(arrayOf("image/*"))
       }) {
           Icon(
               imageVector = Icons.Default.CameraAlt,
               contentDescription = "Open Gallery",
               tint = ScribbleTheme.colors.text
           )
       }
   }
}