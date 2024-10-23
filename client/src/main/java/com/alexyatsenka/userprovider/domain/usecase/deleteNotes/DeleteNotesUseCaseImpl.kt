package com.alexyatsenka.userprovider.domain.usecase.deleteNotes

import com.alexyatsenka.models.domain.Note
import com.alexyatsenka.testcontentprovider.domain.usecase.deleteNotes.DeleteNotesUseCase
import com.alexyatsenka.userprovider.domain.repo.NoteRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class DeleteNotesUseCaseImpl(
    private val noteRepository: NoteRepository
) : DeleteNotesUseCase {
    override suspend fun invoke(notes: List<Note>) = runCatching {
        coroutineScope {
            notes.map { note ->
                async { noteRepository.deleteById(note.id) }
            }.awaitAll()
        }
    }
}