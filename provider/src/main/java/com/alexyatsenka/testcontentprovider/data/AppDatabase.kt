package com.alexyatsenka.testcontentprovider.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexyatsenka.models.data.NoteDB
import com.alexyatsenka.testcontentprovider.data.dao.NoteDao

@Database(
    version = 1,
    entities = [
        NoteDB::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getNoteDao() : NoteDao
}