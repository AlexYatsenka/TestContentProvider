package com.alexyatsenka.userprovider.domain.usecase.updateNote

import com.alexyatsenka.models.domain.Note

interface UpdateNoteUseCase {
    operator fun invoke(note : Note): Result<Int>
}