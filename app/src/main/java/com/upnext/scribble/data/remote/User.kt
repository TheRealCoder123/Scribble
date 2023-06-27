package com.upnext.scribble.data.remote

data class User(
    val displayName: String = "",
    val email: String = "",
    val uid: String = "",
    val username: String = "",
    val image: String = "",
    val bio: String = "",
    val sign_up_finished : Boolean = false
)
