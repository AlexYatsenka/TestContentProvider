package com.alexyatsenka.testcontentprovider.domain.repo

import android.database.Cursor
import com.alexyatsenka.models.data.NoteDB
import com.alexyatsenka.models.domain.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun addNewNote(note : Note) : Long
    fun getNotes() : Flow<List<NoteDB>>
    fun deleteById(id : Long) : Int
    fun updateNote(note : NoteDB) : Int
    fun getNotesSync() : Cursor
    fun getNotesSync(id: Long) : Cursor
}