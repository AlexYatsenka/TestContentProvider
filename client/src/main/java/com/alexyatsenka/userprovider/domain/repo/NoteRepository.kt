package com.alexyatsenka.userprovider.domain.repo

import com.alexyatsenka.models.data.NoteDB
import com.alexyatsenka.models.domain.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun addNewNote(note : Note) : Long
    suspend fun deleteById(id : Long) : Int
    suspend fun updateNote(note : Note) : Int
    fun getNotes() : Flow<List<NoteDB>>
}