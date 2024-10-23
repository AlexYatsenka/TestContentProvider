package com.alexyatsenka.userprovider.data

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import android.util.Log
import com.alexyatsenka.models.data.NoteDB
import com.alexyatsenka.models.domain.Note
import com.alexyatsenka.userprovider.domain.repo.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NoteRepositoryImpl(
    private val contentResolver : ContentResolver
) : NoteRepository {

    private val itemsFlow = MutableStateFlow(emptyList<NoteDB>())

    override suspend fun addNewNote(note: Note): Long {
        val result = contentResolver.insert(
            Uri.parse("content://com.alexyatsenka.testcontentprovider/notes"),
            ContentValues().apply {
                put("id", note.id)
                put("title", note.title)
                put("content", note.content)
            }
        )
        Log.d("Test", "Result after add: $result")
        updateList()
        return 0L
    }

    override fun getNotes() = run {
        updateList()
        itemsFlow.asStateFlow()
    }

    override suspend fun deleteById(id: Long): Int {
        val result = contentResolver.delete(
            ContentUris.withAppendedId(
                Uri.parse("content://com.alexyatsenka.testcontentprovider/notes"),
                id
            ),
            null,
            null
        )
        updateList()
        return result
    }

    override suspend fun updateNote(note: Note): Int {
        val result = contentResolver.update(
            ContentUris.withAppendedId(
                Uri.parse("content://com.alexyatsenka.testcontentprovider/notes"),
                note.id
            ),
            ContentValues().apply {
                put("id", note.id)
                put("title", note.title)
                put("content", note.content)
            },
            null,
            null
        )
        updateList()
        return result
    }

    private fun updateList() {
        val cursor = contentResolver.query(
            Uri.parse("content://com.alexyatsenka.testcontentprovider/notes"),
            arrayOf("id", "title", "content"),
            null, null, null
        )

        cursor?.let {
            if (it.moveToFirst()) {
                val newItems = ArrayList<NoteDB>().apply {
                    do {
                        add(
                            NoteDB(
                                it.getLong(it.getColumnIndexOrThrow("id")),
                                it.getString(it.getColumnIndexOrThrow("title")),
                                it.getString(it.getColumnIndexOrThrow("content"))
                            )
                        )
                    } while (it.moveToNext())
                }
                Log.d("Test", "New data: ${newItems.joinToString { "\n" + it.toString() }}")
                itemsFlow.update { newItems }
            }
            it.close()
        }
    }
}