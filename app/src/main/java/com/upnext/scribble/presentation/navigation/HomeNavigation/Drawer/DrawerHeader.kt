package com.upnext.scribble.presentation.navigation.HomeNavigation.Drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.upnext.scribble.R
import com.upnext.scribble.data.remote.User
import com.upnext.scribble.presentation.global_components.CircleImageView
import com.upnext.scribble.presentation.navigation.HomeNavigation.States.UserState
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme

@Composable
fun DrawerHeader(
    userDataState: UserState,
    onShowWorkSpacesClicked: () -> (Unit) = {},
    onShowWorkSpacesIconChange: () -> (ImageVector) = {Icons.Default.ArrowDownward}
) {

    userDataState.user?.let { user->
        Column(
            modifier = Modifier
                .background(ScribbleTheme.colors.drawerHeaderBackground)
                .fillMaxWidth()
                .height(200.dp)
                .padding(10.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            CircleImageView(
                image = user.image,
                size = 70.dp,
                modifier = Modifier
                    .border(
                        1.dp,
                        Color.LightGray,
                        RoundedCornerShape(70.dp)
                    )
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = user.displayName,
                color = ScribbleTheme.colors.drawerHeaderOnBackground
            )
            Text(
                text = user.username,
                color = Color.LightGray
            )
            Row(
                modifier = Modifier.fillMaxWidth().clickable {
                    onShowWorkSpacesClicked()
                },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = user.email,
                    color = Color.LightGray,
                )
                Icon(
                    imageVector = onShowWorkSpacesIconChange(),
                    contentDescription = stringResource(R.string.show_workspaces),
                    tint = Color.LightGray,
                )
            }
        }
    }

    if (userDataState.loading){
        Box(
            modifier = Modifier
                .background(ScribbleTheme.colors.drawerHeaderBackground)
                .fillMaxWidth()
                .height(200.dp)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }


    if (userDataState.error.isNotBlank()){
        Box(
            modifier = Modifier
                .background(ScribbleTheme.colors.drawerHeaderBackground)
                .fillMaxWidth()
                .height(200.dp)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = userDataState.error, color = ScribbleTheme.colors.error)
        }
    }


}

@Composable
@Preview
fun Preview() {
    DrawerHeader(userDataState = UserState(User("", "", "", "", "", "", false)))
}