package com.upnext.scribble.presentation.navigation.HomeNavigation.States

import com.upnext.scribble.data.remote.Board

data class BoardsState(
    val boards: List<Board> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)
