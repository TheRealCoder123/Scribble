package com.upnext.scribble.data.remote

data class WorkspaceMember (
    val uid: String = "",
    val admin: Boolean = false,
    val can_edit_workspace: Boolean = false,
    val can_create: Boolean = false,
    val can_edit_boards: Boolean = false
)
