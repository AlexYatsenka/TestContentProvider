package com.alexyatsenka.testcontentprovider.domain.usecase.deleteNotes

import com.alexyatsenka.models.domain.Note

interface DeleteNotesUseCase {
    suspend operator fun invoke(notes : List<Note>): Result<List<Int>>
}