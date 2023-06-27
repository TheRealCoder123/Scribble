package com.upnext.scribble.domain.model

data class SignUpUserParams(
    val email: String,
    val password: String,
    val sign_up_finished : Boolean
)
