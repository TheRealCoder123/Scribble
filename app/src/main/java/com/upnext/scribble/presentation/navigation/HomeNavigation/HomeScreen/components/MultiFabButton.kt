package com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.upnext.scribble.presentation.navigation.HomeNavigation.Routes.HomeNavRoutes
import com.upnext.scribble.presentation.navigation.HomeNavigation.States.MultiFabState
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme

@Composable
fun MultiFabButton(
    multiFabState: MultiFabState,
    onStateChanged: (MultiFabState) -> Unit,
    items: List<MiniFabItem>,
    navController: NavController
) {

    val transition = updateTransition(targetState = multiFabState, label = "fab_trans")
    val rotate by transition.animateFloat(label = "fab_rotate") {
        if (it == MultiFabState.Expanded) 315f else 0f
    }

    val rotateMiniFab by transition.animateFloat(label = "fab_rotate") {
        if (it == MultiFabState.Expanded) 360f else 0f
    }

    val textShadow = transition.animateDp(
        label = "text_shadow",
        transitionSpec = { tween(durationMillis = 50) }
    ) {
        if (it == MultiFabState.Expanded) 2.dp else 0.dp
    }.value

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {

        if (transition.currentState == MultiFabState.Expanded){
            items.forEach { item->
                MiniFab(
                    item = item,
                    textShadow = textShadow,
                    showLabel = true,
                    rotateMiniFab
                ){
                    if (item.id == 1){
                        navController.navigate(HomeNavRoutes.CreateBoardRoute.route)
                    }else {
                        navController.navigate(HomeNavRoutes.CreateCardRoute.route)
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
            }
        }

        FloatingActionButton(
            onClick = {
                onStateChanged(
                    if (transition.currentState == MultiFabState.Expanded){
                        MultiFabState.Collapsed
                    }else{
                        MultiFabState.Expanded
                    }
                )
            },
            backgroundColor = ScribbleTheme.colors.secondaryColor,
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.rotate(rotate),
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add FAB",
                tint = Color.White,
            )
        }
    }


}
