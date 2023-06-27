package com.upnext.scribble.presentation.navigation.HomeNavigation.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize().background(ScribbleTheme.colors.background), contentAlignment = Alignment.Center){
        Text(
            text = "Home Screen",
            color = ScribbleTheme.colors.text,
            modifier = Modifier.clickable {
                FirebaseAuth.getInstance().signOut()
            }
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreenPreview() {
    HomeScreen()
}