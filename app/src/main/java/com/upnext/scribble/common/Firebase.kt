package com.upnext.scribble.common

import com.google.firebase.auth.FirebaseAuth
import kotlin.random.Random

object Firebase {

    const val USERS_COLLECTION = "users"

    const val WebClientId = "1058610053842-bi69o2337vuin1l7i37jreopgf6qnirg.apps.googleusercontent.com"

    fun isUserLoggedIn() : Boolean {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser != null
    }

     fun checkEmailVerification(): Boolean {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.isEmailVerified ?: false
    }

}