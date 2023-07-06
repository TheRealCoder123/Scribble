package com.upnext.scribble.data.remote

data class CardList(
    val listId: String = "",
    val listName: String = "",
    val boardId: String = "",
    val orderIndex: Int = 0,
    val cards: List<Card>
)
