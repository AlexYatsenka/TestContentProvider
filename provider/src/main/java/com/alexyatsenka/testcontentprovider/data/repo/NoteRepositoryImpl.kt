package com.alexyatsenka.testcontentprovider.data.repo

import android.database.Cursor
import com.alexyatsenka.models.data.NoteDB
import com.alexyatsenka.models.domain.Note
import com.alexyatsenka.models.domain.toNoteDB
import com.alexyatsenka.testcontentprovider.data.dao.NoteDao
import com.alexyatsenka.testcontentprovider.domain.repo.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao : NoteDao
) : NoteRepository {
    override fun addNewNote(note: Note) : Long {
        return noteDao.addNewNote(note.toNoteDB())
    }

    override fun getNotes(): Flow<List<NoteDB>> {
        return noteDao.getAllNotesAsync()
    }

    override fun deleteById(id: Long): Int {
        return noteDao.deleteById(id)
    }

    override fun updateNote(note: NoteDB): Int {
        return noteDao.update(note)
    }

    override fun getNotesSync(): Cursor {
        return noteDao.getAllNotes()
    }

    override fun getNotesSync(id: Long): Cursor {
        return noteDao.getNoteById(id)
    }
}