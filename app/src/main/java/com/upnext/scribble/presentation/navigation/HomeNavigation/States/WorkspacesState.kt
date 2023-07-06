package com.upnext.scribble.presentation.navigation.HomeNavigation.States

import com.upnext.scribble.data.remote.Workspace

data class WorkspacesState (
    val data: List<Workspace> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
        )