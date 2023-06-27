package com.upnext.scribble.domain.repository

import com.upnext.scribble.common.Resource
import com.upnext.scribble.data.remote.User
import com.upnext.scribble.domain.model.LoginUserParams
import com.upnext.scribble.domain.model.SignUpUserParams
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun loginUser(params: LoginUserParams) : Flow<Resource<String>>
    suspend fun signUpUser(params: SignUpUserParams) : Flow<Resource<String>>
    suspend fun forgotPassword(email: String) : Flow<Resource<String>>
    suspend fun signInWithGoogleUserData(user: User) : Flow<Resource<String>>
}