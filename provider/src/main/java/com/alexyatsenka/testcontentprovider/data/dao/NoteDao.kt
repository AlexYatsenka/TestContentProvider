package com.alexyatsenka.testcontentprovider.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.alexyatsenka.models.data.NoteDB
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun addNewNote(note : NoteDB) : Long

    @Update
    suspend fun update(note: NoteDB) : Int

    @Query("DELETE FROM NoteDB WHERE id = :id")
    suspend fun deleteById(id: Long) : Int

    @Query("SELECT * FROM NoteDB")
    suspend fun getAllNotes() : List<NoteDB>

    @Query("SELECT * FROM NoteDB WHERE id = :id")
    suspend fun getNoteById(id: Long) : NoteDB?

    @Query("SELECT * FROM NoteDB")
    fun getAllNotesAsync() : Flow<List<NoteDB>>
}