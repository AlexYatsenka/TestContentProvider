package com.alexyatsenka.userprovider.di.modules

import com.alexyatsenka.testcontentprovider.domain.usecase.addNote.AddNewNoteUseCase
import com.alexyatsenka.testcontentprovider.domain.usecase.deleteNotes.DeleteNotesUseCase
import com.alexyatsenka.testcontentprovider.domain.usecase.getNotes.GetNotesUseCase
import com.alexyatsenka.userprovider.domain.repo.NoteRepository
import com.alexyatsenka.userprovider.domain.usecase.addNote.AddNewNoteUseCaseImpl
import com.alexyatsenka.userprovider.domain.usecase.deleteNotes.DeleteNotesUseCaseImpl
import com.alexyatsenka.userprovider.domain.usecase.getNotes.GetNotesUseCaseImpl
import com.alexyatsenka.userprovider.domain.usecase.updateNote.UpdateNoteUseCase
import com.alexyatsenka.userprovider.domain.usecase.updateNote.UpdateNoteUseCaseImpl
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

    @Provides
    fun updateNoteUseCaseProvider(
        noteRepository: NoteRepository
    ) : UpdateNoteUseCase = UpdateNoteUseCaseImpl(noteRepository)
}