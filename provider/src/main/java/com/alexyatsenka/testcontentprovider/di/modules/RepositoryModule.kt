package com.alexyatsenka.testcontentprovider.di.modules

import com.alexyatsenka.testcontentprovider.data.dao.NoteDao
import com.alexyatsenka.testcontentprovider.data.repo.NoteRepositoryImpl
import com.alexyatsenka.testcontentprovider.domain.repo.NoteRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun noteRepositoryProvider(
        noteDao : NoteDao
    ) : NoteRepository = NoteRepositoryImpl(noteDao)
}