package com.upnext.scribble.domain.repository

import com.upnext.scribble.common.Resource
import com.upnext.scribble.data.remote.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserData(uid: String) : Flow<Resource<User>>
}