package com.alexyatsenka.testcontentprovider.domain.repo

import com.alexyatsenka.models.data.NoteDB
import com.alexyatsenka.models.domain.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun addNewNote(note : Note) : Long
    fun getNotes() : Flow<List<NoteDB>>
    suspend fun deleteById(id : Long) : Int
    suspend fun updateNote(note : NoteDB) : Int
    suspend fun getNotesSync() : List<NoteDB>
    suspend fun getNotesSync(id: Long) : NoteDB?
}