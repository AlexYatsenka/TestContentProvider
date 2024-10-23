package com.alexyatsenka.userprovider.domain.usecase.updateNote

import com.alexyatsenka.models.domain.Note
import com.alexyatsenka.userprovider.domain.repo.NoteRepository

class UpdateNoteUseCaseImpl(
    private val noteRepository: NoteRepository
) : UpdateNoteUseCase {
    override suspend fun invoke(note: Note) = runCatching {
        noteRepository.updateNote(note)
    }
}