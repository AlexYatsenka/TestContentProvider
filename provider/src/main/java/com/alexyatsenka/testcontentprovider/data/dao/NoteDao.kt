package com.alexyatsenka.testcontentprovider.data.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.alexyatsenka.models.data.NoteDB
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    fun addNewNote(note : NoteDB) : Long

    @Update
    fun update(note: NoteDB) : Int

    @Query("DELETE FROM NoteDB WHERE id = :id")
    fun deleteById(id: Long) : Int

    @Query("SELECT * FROM NoteDB")
    fun getAllNotes() : Cursor

    @Query("SELECT * FROM NoteDB WHERE id = :id")
    fun getNoteById(id: Long) : Cursor

    @Query("SELECT * FROM NoteDB")
    fun getAllNotesAsync() : Flow<List<NoteDB>>
}