package com.alexyatsenka.testcontentprovider.di.modules

import com.alexyatsenka.testcontentprovider.domain.repo.NoteRepository
import com.alexyatsenka.testcontentprovider.domain.usecase.getNotes.GetNotesUseCase
import com.alexyatsenka.testcontentprovider.domain.usecase.getNotes.GetNotesUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun getNotesUseCaseProvide(
        noteRepository: NoteRepository
    ) : GetNotesUseCase = GetNotesUseCaseImpl(noteRepository)
}