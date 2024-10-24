package com.alexyatsenka.userprovider.domain.repo

import com.alexyatsenka.models.data.NoteDB
import com.alexyatsenka.models.domain.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun addNewNote(note : Note) : Long
    fun deleteById(id : Long) : Int
    fun updateNote(note : Note) : Int
    fun getNotes() : Flow<List<NoteDB>>
}