package com.upnext.scribble.domain.repository

import com.upnext.scribble.common.Resource
import com.upnext.scribble.data.remote.Board
import com.upnext.scribble.data.remote.Workspace
import com.upnext.scribble.domain.model.CreateBoardParams
import com.upnext.scribble.domain.model.HomeWorkspaceSections
import kotlinx.coroutines.flow.Flow

interface BoardRepository {
    suspend fun createBoard(params: CreateBoardParams): Flow<Resource<String>>
    suspend fun getBoardsByWorkspace(workspaceId: String) : Flow<Resource<List<Board>>>
    suspend fun getAllBoards(workspaces: List<Workspace>):  Flow<Resource<List<HomeWorkspaceSections>>>
}