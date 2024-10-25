package com.alexyatsenka.userprovider.domain.usecase.deleteNotes

import com.alexyatsenka.models.domain.Note

interface DeleteNotesUseCase {
    operator fun invoke(notes : List<Note>): Result<Unit>
}