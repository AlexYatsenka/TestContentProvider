package com.alexyatsenka.userprovider.di.modules

import android.content.Context
import com.alexyatsenka.userprovider.data.NoteRepositoryImpl
import com.alexyatsenka.userprovider.domain.repo.NoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun noteRepositoryProvider(
        context: Context
    ) : NoteRepository = NoteRepositoryImpl(context.contentResolver)
}