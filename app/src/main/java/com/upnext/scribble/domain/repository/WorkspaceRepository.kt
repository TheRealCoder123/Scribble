package com.upnext.scribble.domain.repository

import com.upnext.scribble.common.Resource
import com.upnext.scribble.data.remote.Workspace
import com.upnext.scribble.domain.model.CreateWorkspaceParams
import kotlinx.coroutines.flow.Flow

interface WorkspaceRepository {
    suspend fun createWorkspace(params: CreateWorkspaceParams) : Flow<Resource<String>>
    suspend fun getWorkspaces() : Flow<Resource<List<Workspace>>>
}