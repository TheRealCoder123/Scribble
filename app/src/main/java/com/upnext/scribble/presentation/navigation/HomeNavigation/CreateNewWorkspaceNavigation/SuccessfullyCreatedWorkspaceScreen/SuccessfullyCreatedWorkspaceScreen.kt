package com.upnext.scribble.presentation.navigation.HomeNavigation.CreateNewWorkspaceNavigation.SuccessfullyCreatedWorkspaceScreen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.palette.graphics.Palette
import coil.compose.rememberImagePainter
import com.upnext.scribble.presentation.global_components.CircleImageView
import com.upnext.scribble.presentation.navigation.HomeNavigation.CreateNewWorkspaceNavigation.CreateWorkspaceViewModel
import com.upnext.scribble.presentation.navigation.HomeNavigation.HomeViewModel
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme
import com.upnext.scribble.common.Helpers.loadBitmap




@Composable
fun SuccessfullyCreatedWorkspaceScreen(
    createWorkspaceVm: CreateWorkspaceViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {

    val context = LocalContext.current

    val userState = homeViewModel.userState.value
    val workspaceState = createWorkspaceVm.createWorkspaceParamsState.value

    val bitmap = context.contentResolver.loadBitmap(Uri.parse(workspaceState.image))!!

    val palette = remember {
        Palette.from(bitmap).generate()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(palette.dominantSwatch?.rgb!!))
    ) {


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 400f
                    )
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            CircleImageView(
                image = workspaceState.image,
                modifier = Modifier
                    .border(
                        1.dp,
                        Color.LightGray,
                        RoundedCornerShape(70.dp)
                    )
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = workspaceState.name,
                color = Color.White,
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = userState.user?.displayName ?: "",
                color = Color.LightGray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = userState.user?.username ?: "",
                color = Color.LightGray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ScribbleTheme.colors.primary
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Invite Members",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Skip",
                color = Color.White,
                modifier = Modifier.clickable {
                    navController.navigateUp()
                }
            )

        }


    }




}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SuccessfullyCreatedWorkspaceScreenPreview() {
    SuccessfullyCreatedWorkspaceScreen(createWorkspaceVm = hiltViewModel(), navController = rememberNavController())
}