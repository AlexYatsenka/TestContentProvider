package com.alexyatsenka.testcontentprovider.domain.usecase.addNote

import com.alexyatsenka.models.domain.Note
import com.alexyatsenka.testcontentprovider.domain.repo.NoteRepository

class AddNewNoteUseCaseImpl(
    private val noteRepository: NoteRepository
) : AddNewNoteUseCase {
    override suspend fun invoke(note: Note) {
        noteRepository.addNewNote(note)
    }
}