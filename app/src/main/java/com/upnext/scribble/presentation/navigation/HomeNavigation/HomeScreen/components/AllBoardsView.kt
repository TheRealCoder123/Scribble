package com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.upnext.scribble.domain.model.HomeWorkspaceSections

@Composable
fun AllBoardsView(
    sections: List<HomeWorkspaceSections>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        items(sections){ section ->
            SectionView(
                title = section.section.displayName,
                workspace = section.section
            ){

            }
            if (section.boards.isNotEmpty()){
                section.boards.forEach { board->
                    HomeBoardView(board = board)
                }
            }else{
                Text(
                    text = "This workspace doesn't have any boards",
                    color = Color.Gray,
                    modifier = Modifier.padding(10.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }



}