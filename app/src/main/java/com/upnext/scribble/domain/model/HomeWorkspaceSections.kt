package com.upnext.scribble.domain.model

import com.upnext.scribble.data.remote.Board
import com.upnext.scribble.data.remote.Workspace

data class HomeWorkspaceSections(
    val section: Workspace,
    val boards: List<Board>
)
