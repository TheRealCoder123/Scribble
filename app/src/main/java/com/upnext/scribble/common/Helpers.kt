package com.upnext.scribble.common

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

object Helpers {

    const val NO_IMAGE_URL = "data:image/jpeg;base64,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"
    @Composable
    inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController) : T {
        val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
        val parentEntry = remember(key1 = this) {
            navController.getBackStackEntry(navGraphRoute)
        }
        return hiltViewModel(parentEntry)
    }

    fun ContentResolver.loadBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream = openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}