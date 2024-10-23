package com.alexyatsenka.models.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexyatsenka.models.domain.Note

@Entity
data class NoteDB(
    @PrimaryKey(autoGenerate = true) val id : Long = 0,
    val title : String,
    val content : String
)

fun NoteDB.toNote() = Note(
    id = id,
    title = title,
    content = content
)