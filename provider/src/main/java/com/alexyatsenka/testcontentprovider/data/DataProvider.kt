package com.alexyatsenka.testcontentprovider.data

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.alexyatsenka.models.data.NoteDB
import com.alexyatsenka.models.domain.Note
import com.alexyatsenka.testcontentprovider.di.Dagger
import com.alexyatsenka.testcontentprovider.domain.repo.NoteRepository
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
                repository.getNotesSync().apply {
                    setNotificationUri(
                        context!!.contentResolver,
                        uri
                    )
                }
            }
            NOTE_ID -> {
                repository.getNotesSync(ContentUris.parseId(uri)).apply {
                    setNotificationUri(context!!.contentResolver, uri)
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
                val id = repository.addNewNote(Note(title = title, content = content))
                ContentUris.withAppendedId(CONTENT_URI, id)
            }
            else -> null
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return when (uriMatcher.match(uri)) {
            NOTE_ID -> {
                val id = ContentUris.parseId(uri)
                repository.deleteById(id)
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
                val updatedNote = NoteDB(
                    id,
                    values?.getAsString("title") ?: "",
                    values?.getAsString("content") ?: ""
                )
                repository.updateNote(updatedNote)
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