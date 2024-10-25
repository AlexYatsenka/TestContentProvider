package com.alexyatsenka.models.domain

import com.alexyatsenka.models.data.NoteDB

data class Note(
    val id : Long = 0,
    val title : String,
    val content : String
)

fun Note.toNoteDB() = NoteDB(
    id = id,
    title = title,
    content = content
)