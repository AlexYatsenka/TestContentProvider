package com.alexyatsenka.testcontentprovider.domain.usecase.getNotes

import com.alexyatsenka.models.data.toNote
import com.alexyatsenka.models.domain.Note
import com.alexyatsenka.testcontentprovider.domain.repo.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCaseImpl(
    private val noteRepo : NoteRepository
) : GetNotesUseCase {
    override fun invoke(): Result<Flow<List<Note>>> = runCatching {
        noteRepo.getNotes().map { it.map { it.toNote() } }
    }
}