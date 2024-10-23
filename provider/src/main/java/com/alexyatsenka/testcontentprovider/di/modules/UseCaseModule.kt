package com.alexyatsenka.testcontentprovider.di.modules

import com.alexyatsenka.testcontentprovider.domain.repo.NoteRepository
import com.alexyatsenka.testcontentprovider.domain.usecase.addNote.AddNewNoteUseCase
import com.alexyatsenka.testcontentprovider.domain.usecase.addNote.AddNewNoteUseCaseImpl
import com.alexyatsenka.testcontentprovider.domain.usecase.deleteNotes.DeleteNotesUseCase
import com.alexyatsenka.testcontentprovider.domain.usecase.deleteNotes.DeleteNotesUseCaseImpl
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

    @Provides
    fun addNewNoteUseCaseProvide(
        noteRepository: NoteRepository
    ) : AddNewNoteUseCase = AddNewNoteUseCaseImpl(noteRepository)

    @Provides
    fun deleteNotesUseCaseProvide(
        noteRepository: NoteRepository
    ) : DeleteNotesUseCase = DeleteNotesUseCaseImpl(noteRepository)
}