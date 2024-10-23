package com.alexyatsenka.userprovider.domain.usecase.updateNote

import com.alexyatsenka.models.domain.Note

interface UpdateNoteUseCase {
    suspend operator fun invoke(note : Note): Result<Int>
}