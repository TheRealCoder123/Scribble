package com.upnext.scribble.data.remote

data class Workspace(
    val workspaceId: String = "",
    val displayName: String = "",
    val image: String = "",
    val members: List<WorkspaceMember> = emptyList(),
    val creatorId: String = ""
)
