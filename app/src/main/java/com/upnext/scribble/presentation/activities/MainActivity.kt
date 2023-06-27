package com.upnext.scribble.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.Identity.getSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.upnext.scribble.presentation.navigation.AuthNavigation.GoogleSignIn.GoogleAuthUIClient
import com.upnext.scribble.presentation.navigation.MainNavigation.MainNavigationGraph
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScribbleTheme {
                val navHostController = rememberNavController()
                Box(modifier = Modifier.fillMaxSize().background(ScribbleTheme.colors.background)){
                    MainNavigationGraph(navHostController = navHostController)
                }
            }
        }
    }
}



