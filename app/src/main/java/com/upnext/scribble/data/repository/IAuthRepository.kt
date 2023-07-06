package com.upnext.scribble.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.upnext.scribble.common.Firebase.USERS_COLLECTION
import com.upnext.scribble.common.Resource
import com.upnext.scribble.data.remote.User
import com.upnext.scribble.domain.model.LoginUserParams
import com.upnext.scribble.domain.model.SignUpUserParams
import com.upnext.scribble.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class IAuthRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : AuthRepository {
    override suspend fun loginUser(params: LoginUserParams): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            auth.signInWithEmailAndPassword(params.email, params.password)
                .await()
            emit(Resource.Success("You have successfully logged in"))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage))
        }
    }

    override suspend fun signUpUser(params: SignUpUserParams): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {

            val uid = auth.createUserWithEmailAndPassword(params.email, params.password)
                .await().user?.uid ?: ""

            firestore.collection(USERS_COLLECTION)
                .document(uid)
                .set(
                    User(
                        "",
                        params.email,
                        uid,
                        params.email.split('@')[0],
                        "",
                        "",
                        params.sign_up_finished
                    )
                ).await()

            emit(Resource.Success("You have successfully signed up"))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage))
        }
    }

    override suspend fun forgotPassword(email: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            auth.sendPasswordResetEmail(email).await()
            emit(Resource.Success("A link has been sent to you email"))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage))
        }
    }

    override suspend fun signInWithGoogleUserData(user: User): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val documentSnapshot = firestore.collection(USERS_COLLECTION).document(user.uid).get().await()

            if (!documentSnapshot.exists()){
                firestore.collection(USERS_COLLECTION).document(user.uid)
                    .set(user, SetOptions.merge()).await()
            }


            emit(Resource.Success("You have logged in successfully"))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage))
        }
    }

}