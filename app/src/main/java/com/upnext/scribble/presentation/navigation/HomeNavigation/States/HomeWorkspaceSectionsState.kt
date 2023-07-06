package com.upnext.scribble.presentation.navigation.HomeNavigation.States

import com.upnext.scribble.domain.model.HomeWorkspaceSections

data class HomeWorkspaceSectionsState (
    val sections: List<HomeWorkspaceSections> = emptyList()
        )