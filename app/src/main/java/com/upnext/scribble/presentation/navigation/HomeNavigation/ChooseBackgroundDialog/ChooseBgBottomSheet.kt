package com.upnext.scribble.presentation.navigation.HomeNavigation.ChooseBackgroundDialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlin.random.Random

@Composable
fun ChooseBgDialog(
    isDialogOpen: Boolean = true,
    onBackgroundSelected: (String?, Long?) -> Unit
) {

    if (isDialogOpen){
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                usePlatformDefaultWidth = true
            )
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0xFFF4E7E6))
            ){
                items(getColors()){
                    Box(
                        modifier = Modifier
                            .background(Color(it))
                            .size(100.dp)
                            .clickable {
                                onBackgroundSelected(null, it)
                            }
                    )
                }
            }
        }
    }


}

private fun getColors() : MutableList<Long> {
    val list : MutableList<Long> = mutableListOf()
    for (i in 0..25){
        val color = Random.nextLong()
        list.add(color)
    }
    return list
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun Preview() {
    ChooseBgDialog { image, color ->

    }
}