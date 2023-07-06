package com.upnext.scribble.domain.use_case.auth_use_cases

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class GetSignedInUserUIDUseCase @Inject constructor(
    private val auth: FirebaseAuth
){
    operator fun invoke() : String? = auth.uid
}