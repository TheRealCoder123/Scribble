package com.upnext.scribble.domain.model

data class CreateBoardParams (
    val boardName: String = "",
    val workspaceId: String = "",
    val backgroundColor: Long = 0,
    val backgroundImage: String = "",
        )