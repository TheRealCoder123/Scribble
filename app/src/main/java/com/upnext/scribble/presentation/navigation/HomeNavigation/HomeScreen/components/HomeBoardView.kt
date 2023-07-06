package com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upnext.scribble.data.remote.Board
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme

@Composable
fun HomeBoardView(
    board: Board
) {

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp).fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(board.backgroundColor))
        )
        Spacer(modifier = Modifier.width(ScribbleTheme.spaces.large))
        Text(
            text = board.boardName,
            color = ScribbleTheme.colors.text,
            maxLines = 1
        )
    }

}

@Composable
@Preview
fun Preview() {
    HomeBoardView(Board("Hello World", backgroundColor = 144536234L))
}