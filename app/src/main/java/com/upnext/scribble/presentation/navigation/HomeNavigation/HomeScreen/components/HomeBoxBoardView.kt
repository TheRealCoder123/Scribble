package com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.upnext.scribble.data.remote.Board

@Composable
fun HomeBoxBoardView(
    board: Board,
    width: Dp = 180.dp,
    height: Dp = 140.dp,
) {
    
    Box(
        modifier = Modifier.size(width = width, height = height)
            .padding(5.dp)
            .clip(RoundedCornerShape(5.dp)),
        contentAlignment = Alignment.BottomCenter,
    ){

        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(board.backgroundColor)))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x9C000000))
        ){
            Text(
                text = board.boardName,
                color = Color.White,
                modifier = Modifier.padding(vertical = 15.dp, horizontal = 10.dp),
                maxLines = 1
            )
        }

    }
    
}