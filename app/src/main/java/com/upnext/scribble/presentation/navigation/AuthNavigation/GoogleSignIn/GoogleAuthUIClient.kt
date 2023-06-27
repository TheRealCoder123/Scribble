package com.upnext.scribble.presentation.navigation.AuthNavigation.GoogleSignIn

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.upnext.scribble.data.remote.User
import kotlinx.coroutines.tasks.await

class GoogleAuthUIClient(
    private val context: Context,
    private val oneTapClient: SignInClient,
) {
    private val auth = Firebase.auth

    suspend fun signIn() : IntentSender? {

        val result = try {
            oneTapClient.beginSignIn(buildSignInRequest())
                .await()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent) : SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            val displayName = user?.displayName ?: ""
            val username = user?.email?.split('@')?.get(0) ?: ""
            SignInResult(
                data = user?.run {
                    User(
                        displayName = displayName,
                        email = email ?: "",
                        uid = uid,
                        username = username,
                        image = photoUrl.toString(),
                        bio = "",
                        sign_up_finished = true
                    )
                },
                null
            )
        }catch (e: Exception) {
            e.printStackTrace()
            SignInResult(null, e.localizedMessage ?:  "")
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }


    private fun buildSignInRequest() : BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(com.upnext.scribble.common.Firebase.WebClientId)
                    .build()
            )
            .setAutoSelectEnabled(true).build()
    }

}