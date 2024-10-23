package com.alexyatsenka.testcontentprovider.data.repo

import com.alexyatsenka.models.data.NoteDB
import com.alexyatsenka.models.domain.Note
import com.alexyatsenka.models.domain.toNoteDB
import com.alexyatsenka.testcontentprovider.data.dao.NoteDao
import com.alexyatsenka.testcontentprovider.domain.repo.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao : NoteDao
) : NoteRepository {
    override suspend fun addNewNote(note: Note) : Long {
        return noteDao.addNewNote(note.toNoteDB())
    }

    override fun getNotes(): Flow<List<NoteDB>> {
        return noteDao.getAllNotesAsync()
    }

    override suspend fun deleteById(id: Long): Int {
        return noteDao.deleteById(id)
    }

    override suspend fun updateNote(note: NoteDB): Int {
        return noteDao.update(note)
    }

    override suspend fun getNotesSync(): List<NoteDB> {
        return noteDao.getAllNotes()
    }

    override suspend fun getNotesSync(id: Long): NoteDB? {
        return noteDao.getNoteById(id)
    }
}