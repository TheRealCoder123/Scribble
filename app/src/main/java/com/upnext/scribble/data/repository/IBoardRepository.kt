package com.upnext.scribble.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.upnext.scribble.common.Firebase
import com.upnext.scribble.common.Resource
import com.upnext.scribble.data.remote.Board
import com.upnext.scribble.data.remote.Workspace
import com.upnext.scribble.domain.model.CreateBoardParams
import com.upnext.scribble.domain.model.HomeWorkspaceSections
import com.upnext.scribble.domain.repository.BoardRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class IBoardRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : BoardRepository {
    override suspend fun createBoard(params: CreateBoardParams): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {

            val boardId = firestore.collection(Firebase.BOARDS_COLLECTION)
                .document().id
            val board = Board(
                params.boardName,
                params.workspaceId,
                params.backgroundColor,
                params.backgroundImage,
                boardId,
                auth.uid ?: ""
            )
            firestore.collection(Firebase.BOARDS_COLLECTION)
                .document(boardId).set(board, SetOptions.merge())
                .await()
            emit(Resource.Success("Board has been successfully created"))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage))
        }
    }

    override suspend fun getBoardsByWorkspace(workspaceId: String): Flow<Resource<List<Board>>> = callbackFlow {
        trySend(Resource.Loading()).isSuccess
        val listener = firestore.collection(Firebase.BOARDS_COLLECTION)
            .whereEqualTo("workspaceId", workspaceId).addSnapshotListener { value, error ->
                value?.let {
                    val boards = it.toObjects(Board::class.java)
                    trySend(Resource.Success(boards))
                }
                error?.let { e ->
                    trySend(Resource.Error(e.localizedMessage)).isSuccess
                }
            }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun getAllBoards(workspaces: List<Workspace>): Flow<Resource<List<HomeWorkspaceSections>>> = flow {
        val sections: MutableList<HomeWorkspaceSections> = mutableListOf()
        emit(Resource.Loading())
        for (workspace in workspaces) {
            val boards = firestore.collection(Firebase.BOARDS_COLLECTION)
                .whereEqualTo("workspaceId", workspace.workspaceId)
                .get().await().toObjects(Board::class.java)
            sections.add(HomeWorkspaceSections(workspace, boards))
        }
        emit(Resource.Success(sections))
    }

}