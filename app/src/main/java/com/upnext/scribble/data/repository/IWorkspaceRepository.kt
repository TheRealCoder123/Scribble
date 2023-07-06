package com.upnext.scribble.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.StorageReference
import com.upnext.scribble.common.Firebase
import com.upnext.scribble.common.Resource
import com.upnext.scribble.data.remote.Workspace
import com.upnext.scribble.data.remote.WorkspaceMember
import com.upnext.scribble.domain.model.CreateWorkspaceParams
import com.upnext.scribble.domain.repository.WorkspaceRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class IWorkspaceRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: StorageReference,
    private val auth: FirebaseAuth
) : WorkspaceRepository {
    override suspend fun createWorkspace(params: CreateWorkspaceParams): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val workspaceId = firestore.collection(Firebase.WORKSPACES_COLLECTION).document().id

            var url: String = ""

            if (params.image.isNotBlank()){
                url = storage.child(Firebase.WORKSPACES_STORAGE_FOLDER)
                    .child(workspaceId)
                    .child("image").putFile(Uri.parse(params.image))
                    .await().metadata?.reference?.downloadUrl?.await().toString()
            }

            val workspace = Workspace(
                workspaceId,
                params.name,
                url,
                listOf(
                    WorkspaceMember(
                        uid =auth.uid ?: "",
                        admin = true,
                        can_edit_workspace = true,
                        can_create = true,
                        can_edit_boards = true
                    )
                ),
                auth.uid ?: ""
            )

            firestore.collection(Firebase.WORKSPACES_COLLECTION)
                .document(workspaceId).set(workspace, SetOptions.merge())
                .await()

            emit(Resource.Success("Workspace has been successfully created"))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage))
        }
    }

    override suspend fun getWorkspaces(): Flow<Resource<List<Workspace>>> = callbackFlow {

        val userWorkspaces: MutableList<Workspace> = mutableListOf()

        trySend(Resource.Loading()).isSuccess
        val listener = firestore.collection(Firebase.WORKSPACES_COLLECTION)
            .addSnapshotListener { value, error ->

                value?.let { snapshot ->
                    val workspaces = snapshot.toObjects(Workspace::class.java)
                    workspaces.forEach {  workspace->
                        workspace.members.forEach { memeber ->
                            if (memeber.uid == auth.uid){
                                userWorkspaces.add(workspace)
                            }
                        }
                    }
                    trySend(Resource.Success(userWorkspaces)).isSuccess
                }

                error?.let {
                    trySend(Resource.Error(it.localizedMessage)).isSuccess
                }

            }

        awaitClose {
            listener.remove()
        }
    }


}