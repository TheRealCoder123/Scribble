package com.upnext.scribble.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.upnext.scribble.common.Firebase
import com.upnext.scribble.common.Resource
import com.upnext.scribble.data.remote.User
import com.upnext.scribble.data.remote.Workspace
import com.upnext.scribble.domain.repository.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class IUserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserRepository {
    override suspend fun getUserData(uid: String): Flow<Resource<User>> = callbackFlow {

        trySend(Resource.Loading()).isSuccess

        val listener = firestore.collection(Firebase.USERS_COLLECTION)
            .document(uid).addSnapshotListener { value, error ->

                value?.let {
                    val user = it.toObject(User::class.java)
                    trySend(Resource.Success(user)).isSuccess
                }

                error?.let {
                    trySend(Resource.Error(it.localizedMessage)).isSuccess
                }

            }
        awaitClose { listener.remove() }
    }
}