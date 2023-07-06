package com.upnext.scribble.domain.use_case.board_use_cases

import android.util.Log
import com.upnext.scribble.common.Resource
import com.upnext.scribble.data.remote.Board
import com.upnext.scribble.data.remote.Workspace
import com.upnext.scribble.data.repository.IBoardRepository
import com.upnext.scribble.domain.model.HomeWorkspaceSections
import com.upnext.scribble.domain.repository.BoardRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class GetBoardsByAllWorkspacesUseCase @Inject constructor(
    private val boardRepository: IBoardRepository
){

    operator fun invoke(workspaces: List<Workspace>): Flow<Resource<List<HomeWorkspaceSections>>> = callbackFlow {
        val sections: MutableList<HomeWorkspaceSections> = mutableListOf()

        trySend(Resource.Loading()).isSuccess
        Log.e("workspaces list size", "${workspaces.size}")

        for (workspace in workspaces) {
            Log.e("workspaces", "$workspace")
            boardRepository.getBoardsByWorkspace(workspace.workspaceId).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        trySend(Resource.Error(result.message)).isSuccess
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        sections.add(HomeWorkspaceSections(workspace, result.data ?: emptyList()))
                        Log.e("sections", "$sections")
                        trySend(Resource.Success(sections.toList())).isSuccess
                    }
                }
            }
        }
        awaitClose {
        }
    }
}
