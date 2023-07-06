package com.upnext.scribble.data.remote

data class Card(
    val cardId: String = "",
    val name: String = "",
    val description: String = "",
    val attachedImage: String = "",
    val startDate: Long,
    val dueDate: Long
)
