package com.alexyatsenka.testcontentprovider.domain.usecase.getNotes

import com.alexyatsenka.models.domain.Note
import kotlinx.coroutines.flow.Flow

interface GetNotesUseCase {
    operator fun invoke() : Result<Flow<List<Note>>>
}