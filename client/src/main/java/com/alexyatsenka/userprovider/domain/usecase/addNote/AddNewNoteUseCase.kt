package com.alexyatsenka.testcontentprovider.domain.usecase.addNote

import com.alexyatsenka.models.domain.Note

interface AddNewNoteUseCase {
    operator fun invoke(note : Note)
}