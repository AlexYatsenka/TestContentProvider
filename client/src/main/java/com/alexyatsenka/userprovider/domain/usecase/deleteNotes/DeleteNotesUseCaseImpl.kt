package com.alexyatsenka.userprovider.domain.usecase.deleteNotes

import com.alexyatsenka.models.domain.Note
import com.alexyatsenka.userprovider.domain.repo.NoteRepository

class DeleteNotesUseCaseImpl(
    private val noteRepository: NoteRepository
) : DeleteNotesUseCase {
    override fun invoke(notes: List<Note>) = runCatching {
        notes.forEach { note ->
            noteRepository.deleteById(note.id)
        }
    }
}