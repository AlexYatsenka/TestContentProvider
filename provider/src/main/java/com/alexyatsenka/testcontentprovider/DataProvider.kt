package com.alexyatsenka.testcontentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.alexyatsenka.models.domain.Note
import com.alexyatsenka.testcontentprovider.di.Dagger
import com.alexyatsenka.testcontentprovider.domain.repo.NoteRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DataProvider : ContentProvider() {

    @Inject
    lateinit var repository: NoteRepository

    override fun onCreate() = run {
        Dagger.buildAppComponent(context!!).inject(this)
        true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            NOTES -> {
                val notes = runBlocking { repository.getNotesSync() }
                MatrixCursor(arrayOf("id", "title", "content")).apply {
                    notes.forEach { note ->
                        addRow(arrayOf(note.id, note.title, note.content))
                    }
                }
            }
            NOTE_ID -> {
                val id = ContentUris.parseId(uri)
                runBlocking { repository.getNotesSync(id) }?.let {
                    MatrixCursor(arrayOf("id", "title", "content")).apply {
                        addRow(arrayOf(it.id, it.title, it.content))
                    }
                }
            }
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            NOTES -> "vnd.android.cursor.dir/vnd.$AUTHORITY"
            NOTE_ID -> "vnd.android.cursor.item/vnd.$AUTHORITY"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when (uriMatcher.match(uri)) {
            NOTES -> {
                val title = values?.getAsString("title") ?: return null
                val content = values.getAsString("content") ?: return null
                val id = runBlocking { repository.addNewNote(Note(title = title, content = content)) }
                ContentUris.withAppendedId(CONTENT_URI, id)
            }
            else -> null
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return when (uriMatcher.match(uri)) {
            NOTE_ID -> {
                val id = ContentUris.parseId(uri)
                runBlocking { repository.deleteById(id) }
            }
            else -> 0
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return when (uriMatcher.match(uri)) {
            NOTE_ID -> {
                val id = ContentUris.parseId(uri)
                val note = runBlocking { repository.getNotesSync(id) } ?: return 0
                val updatedNote = note.copy(
                    title = values?.getAsString("title") ?: note.title,
                    content = values?.getAsString("content") ?: note.content
                )
                runBlocking { repository.updateNote(updatedNote) }
            }
            else -> 0
        }
    }

    companion object {
        const val AUTHORITY = "com.alexyatsenka.testcontentprovider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/notes")

        private const val NOTES = 1
        private const val NOTE_ID = 2

        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "notes", NOTES)
            addURI(AUTHORITY, "notes/#", NOTE_ID)
        }
    }
}